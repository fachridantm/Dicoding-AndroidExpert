package com.dicoding.githubapp.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(

    @Json(name = "total_count")
    val amount: Int? = 0,

    @Json(name = "items")
    val items: List<UserItem>? = null
)