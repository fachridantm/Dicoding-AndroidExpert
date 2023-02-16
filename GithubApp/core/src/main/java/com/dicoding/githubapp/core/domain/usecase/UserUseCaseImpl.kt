package com.dicoding.githubapp.core.domain.usecase

import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.domain.model.Follow
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository,
) : UserUseCase {

    override fun searchUser(query: String): Flow<Resource<List<User>>> =
        userRepository.searchUser(query)

    override fun getDetailUser(username: String?): Flow<Resource<User>> =
        userRepository.getDetailUser(username)

    override fun getUserFollowers(username: String): Flow<Resource<List<Follow>>> =
        userRepository.getUserFollowers(username)

    override fun getUserFollowing(username: String): Flow<Resource<List<Follow>>> =
        userRepository.getUserFollowing(username)

    override fun getFavoritedUsers(): Flow<Resource<List<UserEntity>>> =
        userRepository.getFavoritedUsers()

    override suspend fun insertUser(user: UserEntity) = userRepository.insertUser(user)

    override suspend fun deleteUser(user: UserEntity) = userRepository.deleteUser(user)

    override fun getThemeSetting(): Flow<Boolean> = userRepository.getThemeSetting()

    override suspend fun setThemeSetting(newSetting: Boolean) =
        userRepository.setThemeSetting(newSetting)
}