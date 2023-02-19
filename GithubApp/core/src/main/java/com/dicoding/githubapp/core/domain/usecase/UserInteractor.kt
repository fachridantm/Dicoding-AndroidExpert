package com.dicoding.githubapp.core.domain.usecase

import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: IUserRepository,
) : UserUseCase {

    override fun searchUser(query: String): Flow<Resource<List<User>>> =
        userRepository.searchUser(query)

    override fun getDetailUser(username: String?): Flow<Resource<User>> =
        userRepository.getDetailUser(username)

    override fun getUserFollowers(username: String): Flow<Resource<List<User>>> =
        userRepository.getUserFollowers(username)

    override fun getUserFollowing(username: String): Flow<Resource<List<User>>> =
        userRepository.getUserFollowing(username)

    override fun getFavoritedUsers(): Flow<List<User>> = userRepository.getFavoritedUsers()
    override suspend fun setFavoriteUsers(user: User, state: Boolean) =
        userRepository.setFavoriteUsers(user, state)

    override fun getThemeSetting(): Flow<Boolean> = userRepository.getThemeSetting()

    override suspend fun setThemeSetting(newSetting: Boolean) =
        userRepository.setThemeSetting(newSetting)
}