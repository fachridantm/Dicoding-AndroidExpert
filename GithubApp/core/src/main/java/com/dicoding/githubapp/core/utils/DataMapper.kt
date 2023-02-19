package com.dicoding.githubapp.core.utils

import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.data.source.remote.response.FollowItem
import com.dicoding.githubapp.core.data.source.remote.response.UserItem
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
        url = user.url,
        followingUrl = "",
        followersUrl = ""
    )

    fun followResponsetoUser(follow: FollowItem): User = User(
        username = follow.username,
        name = "",
        avatar = follow.avatar,
        company = "-",
        location = "-",
        repository = 0,
        followers = 0,
        following = 0,
        isFavorited = false,
        url = follow.url,
        followingUrl = follow.followingUrl,
        followersUrl = follow.followersUrl
    )

    fun userEntitytoUser(user: List<UserEntity>): List<User> = user.map {
        User(
            username = it.username,
            name = "",
            avatar = it.avatar ?: "",
            company = "",
            location = "",
            repository = 0,
            followers = 0,
            following = 0,
            isFavorited = it.isFavorited,
            url = "",
            followingUrl = "",
            followersUrl = ""
        )
    }

    fun usertoUserEntity(user: User) = UserEntity(
        username = user.username,
        avatar = user.avatar,
        isFavorited = user.isFavorited
    )
}