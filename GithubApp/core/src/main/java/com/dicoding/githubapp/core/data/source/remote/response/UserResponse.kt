package com.dicoding.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val amount: Int? = 0,

    @field:SerializedName("items")
    val items: List<UserItem>? = null
)