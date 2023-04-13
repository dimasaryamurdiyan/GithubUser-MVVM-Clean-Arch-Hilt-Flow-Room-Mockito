package com.testcase.githubapp.ui.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.testcase.githubapp.databinding.ItemUsersBinding
import com.testcase.githubapp.domain.model.Users
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.utils.loadImage

class UsersAdapter(private val itemClick: OnClickListener): RecyclerView.Adapter<UsersAdapter.ViewHolder>()  {

    private val diffCallback = object : DiffUtil.ItemCallback<GetUserResponse.User>() {
        override fun areItemsTheSame(
            oldItem: GetUserResponse.User,
            newItem: GetUserResponse.User
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetUserResponse.User,
            newItem: GetUserResponse.User
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<GetUserResponse.User>?) = differ.submitList(value)

    inner class ViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetUserResponse.User){
            binding.apply {
                ivProfile.loadImage(
                    url = item.avatarUrl ?: "",
                )
                tvEmail.text = item.email
                tvLocation.text = item.location
                tvName.text = item.name
                tvPosition.text = item.company
                tvUsername.text = item.login

                binding.root.setOnClickListener {
                    itemClick.pickChoice(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemUsersBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: UsersAdapter.ViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface OnClickListener {
        fun pickChoice(item: GetUserResponse.User)
    }
}