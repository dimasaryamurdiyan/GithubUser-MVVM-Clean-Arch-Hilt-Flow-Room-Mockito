package com.testcase.githubapp.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.testcase.githubapp.R
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.databinding.FragmentUsersBinding
import com.testcase.githubapp.domain.model.Users
import com.testcase.githubapp.ui.BaseFragment
import com.testcase.githubapp.ui.user.adapter.UsersAdapter
import com.testcase.githubapp.utils.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class UsersFragment : BaseFragment() {
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: FragmentUsersBinding

    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchViewModelCallback()
        onViewObserve()
        onViewBind()
    }

    private fun onViewBind() {
        binding.apply {
            etSearch.doAfterTextChanged { text ->
                val data = text.toString()
                lifecycleScope.launch {
                    delay(SEARCH_DEBOUNCE_DURATION)
                    if(data.isNotEmpty()) {
                        viewModel.getSearchUsers(data)
                    } else {
                        viewModel.getUsers()
                    }
                }
            }
            etSearch.doOnTextChanged { text, start, before, count ->

            }
        }
    }

    private fun fetchViewModelCallback() {
        viewModel.getUsers()
    }

    private fun onViewObserve() {
        viewModel.apply {
            userList.observe(viewLifecycleOwner){
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.rvUsers.visibility = View.VISIBLE
                        hideLoading()
                        val data = it.data
                        Log.d("UserObserve", "$data")
                        if (!data.isNullOrEmpty()) {
                           setupView(data)
                        }
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun setupView(data: GetUserResponse) {
        Log.d("UsersFragment", "data: $data")
        val users = ArrayList<Users.User>()
        data.forEach {
            users.add(
                Users.User(
                    id = it.id,
                    login = it.login,
                    location = it.location,
                    company = it.company,
                    avatarUrl = it.avatarUrl,
                    name = it.name,
                    email = it.email
                )
            )
        }
        Log.d("UsersFragment", "users: $users")
        usersAdapter = UsersAdapter(object : UsersAdapter.OnClickListener{
            override fun pickChoice(item: GetUserResponse.User) {
                binding.etSearch.setText("")
                //navigate to detail user
                findNavController().navigate(
                    R.id.actionUsersFragmentToDetailUserFragment,
                    bundleOf("username" to item.login)
                )
            }

        })

        usersAdapter.submitData(data)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        binding.rvUsers.adapter = usersAdapter
    }

    override fun onResume() {
        super.onResume()
        binding.etSearch.setText("")
    }

    companion object{
        const val SEARCH_DEBOUNCE_DURATION = 400L
    }
}