package com.dicoding.githubapp.core.domain.model

data class Follow(
    val username: String,
    val avatar: String,
    val url: String,
    val followingUrl: String,
    val followersUrl: String,
)
