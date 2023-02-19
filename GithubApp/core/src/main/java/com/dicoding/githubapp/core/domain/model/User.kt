package com.dicoding.githubapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val avatar: String,
    val company: String,
    val location: String,
    val repository: Int,
    val followers: Int,
    val following: Int,
    val url: String,
    val followingUrl: String,
    val followersUrl: String,
    var isFavorited: Boolean? = false
) : Parcelable
