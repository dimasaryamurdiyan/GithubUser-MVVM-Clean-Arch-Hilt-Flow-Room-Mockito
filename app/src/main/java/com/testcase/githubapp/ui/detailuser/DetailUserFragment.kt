package com.testcase.githubapp.ui.detailuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.testcase.githubapp.data.Resource
import com.testcase.githubapp.data.remote.response.GetUserReposResponse
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.databinding.FragmentDetailUserBinding
import com.testcase.githubapp.domain.model.UserRepos
import com.testcase.githubapp.ui.BaseFragment
import com.testcase.githubapp.ui.detailuser.adapter.UsersRepoAdapter
import com.testcase.githubapp.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserFragment : BaseFragment() {

    private val viewModel by viewModels<DetailUserViewModel>()

    private lateinit var binding: FragmentDetailUserBinding

    private lateinit var username: String

    private lateinit var userReposAdapter: UsersRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString("username").toString()

        fetchViewModelCallback()
        onViewObserve()
    }

    private fun onViewObserve() {
        viewModel.apply {
            user.observe(viewLifecycleOwner){
                when(it.status) {
                    Resource.Status.SUCCESS -> {
                        hideLoading()
                        val data = it.data
                        setupView(data)
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

            userRepos.observe(viewLifecycleOwner){
                when(it.status) {
                    Resource.Status.SUCCESS -> {
                        hideLoading()
                        val data = it.data
                        setupViewRepos(data)
                        Log.d("DetailUser", "repos: $data")
                    }
                    Resource.Status.ERROR -> {
                        hideLoading()
                        Log.d("DetailUser", "repos: ${it.message}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun setupViewRepos(data: GetUserReposResponse?) {
        val repos = ArrayList<UserRepos.UserReposItem>()
        data?.forEach {
            repos.add(
                UserRepos.UserReposItem(
                    id = it.id,
                    owner = it.owner,
                    description = it.description,
                    stargazersCount = it.stargazersCount,
                    updatedAt = it.updatedAt,
                    name = it.name
                )
            )
        }
        userReposAdapter = UsersRepoAdapter()
        userReposAdapter.submitData(repos)
        binding.apply {
            rvRepos.adapter = userReposAdapter
            rvRepos.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupView(data: GetUserResponse.User?) {
        binding.apply {
            ivProfile.loadImage(data?.avatarUrl ?: "")
            tvName.text = data?.name
            tvEmail.text = data?.email
            tvLocation.text = data?.location
            tvPosition.text = data?.company
            tvUsername.text = data?.login
        }
    }

    private fun fetchViewModelCallback() {
        viewModel.apply {
            getUser(username)
            getUserRepos(username)
        }
    }
}