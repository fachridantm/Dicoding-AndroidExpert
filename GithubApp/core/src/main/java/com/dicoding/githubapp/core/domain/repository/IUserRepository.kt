package com.dicoding.githubapp.core.domain.repository

import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.domain.model.Follow
import com.dicoding.githubapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun searchUser(query: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String?): Flow<Resource<User>>
    fun getUserFollowers(username: String): Flow<Resource<List<Follow>>>
    fun getUserFollowing(username: String): Flow<Resource<List<Follow>>>
    fun getFavoritedUsers(): Flow<Resource<List<UserEntity>>>
    suspend fun insertUser(user: UserEntity, isFavorited: Boolean)
    suspend fun deleteUser(user: UserEntity, isFavorited: Boolean)
    fun getThemeSetting(): Flow<Boolean>
    suspend fun setThemeSetting(newSetting: Boolean)
    suspend fun isUserFavorited(username: String): Boolean
}