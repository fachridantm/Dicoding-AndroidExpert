package com.dicoding.githubapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubapp.data.model.entity.UserEntity

class UserDiffUtils(private val oldList: List<UserEntity>, private val newList: List<UserEntity>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList == newList

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition].username
        val latest = newList[newItemPosition].username
        return old == latest
    }
}