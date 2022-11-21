package com.dicoding.githubapp.core.utils

import com.dicoding.githubapp.core.data.source.remote.response.FollowItem
import com.dicoding.githubapp.core.data.source.remote.response.UserItem
import com.dicoding.githubapp.core.domain.model.Follow
import com.dicoding.githubapp.core.domain.model.User

object DataMapper {
    fun userResponseToUser(user: UserItem): User = User(
        username = user.username,
        name = user.name ?: "-",
        avatar = user.avatar,
        company = user.company ?: "-",
        location = user.location ?: "-",
        repository = user.repository,
        followers = user.followers,
        following = user.following,
        isFavorited = false,
        url = user.url
    )

    fun followResponsetoFollow(follow: FollowItem): Follow = Follow(
        username = follow.username,
        avatar = follow.avatar,
        url = follow.url,
        followingUrl = follow.followingUrl,
        followersUrl = follow.followersUrl
    )
}