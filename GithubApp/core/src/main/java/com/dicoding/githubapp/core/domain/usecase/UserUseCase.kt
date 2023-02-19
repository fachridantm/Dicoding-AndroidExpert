package com.dicoding.githubapp.core.domain.usecase

import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun searchUser(query: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String?): Flow<Resource<User>>
    fun getUserFollowers(username: String): Flow<Resource<List<User>>>
    fun getUserFollowing(username: String): Flow<Resource<List<User>>>
    fun getFavoritedUsers(): Flow<List<User>>
    suspend fun setFavoriteUsers(user: User, state: Boolean)
    fun getThemeSetting(): Flow<Boolean>
    suspend fun setThemeSetting(newSetting: Boolean)
}