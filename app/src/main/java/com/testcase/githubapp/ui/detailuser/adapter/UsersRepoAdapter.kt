package com.testcase.githubapp.ui.detailuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.testcase.githubapp.R
import com.testcase.githubapp.data.remote.response.GetUserResponse
import com.testcase.githubapp.databinding.ItemReposBinding
import com.testcase.githubapp.databinding.ItemUsersBinding
import com.testcase.githubapp.domain.model.UserRepos
import com.testcase.githubapp.utils.Utils
import com.testcase.githubapp.utils.compoundDrawable
import com.testcase.githubapp.utils.loadImage

class UsersRepoAdapter(): RecyclerView.Adapter<UsersRepoAdapter.ViewHolder>()  {

    private val diffCallback = object : DiffUtil.ItemCallback<UserRepos.UserReposItem>() {
        override fun areItemsTheSame(
            oldItem: UserRepos.UserReposItem,
            newItem: UserRepos.UserReposItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserRepos.UserReposItem,
            newItem: UserRepos.UserReposItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<UserRepos.UserReposItem>?) = differ.submitList(value)

    inner class ViewHolder(private val binding: ItemReposBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserRepos.UserReposItem){
            binding.apply {
                ivProfile.loadImage(
                    url = item.owner?.avatarUrl ?: "",
                )
                tvName.text = item.name
                tvDescription.text = item.description
                tvStarsCount.apply {
                    text = item.stargazersCount.toString()
                    compoundDrawable(start = R.drawable.ic_star_16dp)
                }
                tvLastUpdate.text = Utils.convertUTCTtoDateHalfMonthYears(item.updatedAt)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersRepoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemReposBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: UsersRepoAdapter.ViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

}