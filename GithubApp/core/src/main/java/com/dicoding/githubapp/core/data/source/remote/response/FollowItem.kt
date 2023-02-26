package com.dicoding.githubapp.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FollowItem(

    @Json(name = "following_url")
    val followingUrl: String,

    @Json(name = "login")
    val username: String,

    @Json(name = "followers_url")
    val followersUrl: String,

    @Json(name = "avatar_url")
    val avatar: String,

    @Json(name = "html_url")
    val url: String,
)
