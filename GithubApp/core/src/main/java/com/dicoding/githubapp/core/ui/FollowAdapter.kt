package com.dicoding.githubapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubapp.core.databinding.ItemRowUserBinding
import com.dicoding.githubapp.core.domain.model.Follow
import com.dicoding.githubapp.core.utils.loadUserImage

class FollowAdapter(private val onItemClick: (Follow) -> Unit) :
    ListAdapter<Follow, FollowAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val userBinding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(user: Follow) {
            userBinding.apply {
                tvItemUsername.text = user.username
                ivItemAvatar.loadUserImage(user.avatar)
                root.setOnClickListener {
                    onItemClick(user)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userBinding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Follow>() {
            override fun areItemsTheSame(oldItem: Follow, newItem: Follow): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: Follow, newItem: Follow): Boolean {
                return oldItem == newItem
            }
        }
    }
}