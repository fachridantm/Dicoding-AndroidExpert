package com.dicoding.githubapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserItem(

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("public_repos")
    val repository: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatar: String,

    @field:SerializedName("html_url")
    val url: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null
)
