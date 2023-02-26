package com.dicoding.githubapp.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserItem(

    @Json(name = "login")
    val username: String,

    @Json(name = "company")
    val company: String? = null,

    @Json(name = "public_repos")
    val repository: Int,

    @Json(name = "followers")
    val followers: Int,

    @Json(name = "avatar_url")
    val avatar: String,

    @Json(name = "html_url")
    val url: String,

    @Json(name = "following")
    val following: Int,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "location")
    val location: String? = null
)
