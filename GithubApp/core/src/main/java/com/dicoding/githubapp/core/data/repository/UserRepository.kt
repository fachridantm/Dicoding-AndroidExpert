package com.dicoding.githubapp.core.data.repository

import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.data.source.local.LocalDataSource
import com.dicoding.githubapp.core.data.source.remote.RemoteDataSource
import com.dicoding.githubapp.core.data.source.remote.network.ApiResponse
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.domain.repository.IUserRepository
import com.dicoding.githubapp.core.utils.AppExecutors
import com.dicoding.githubapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors,
) : IUserRepository {
    override fun searchUser(query: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.searchUser(query).first()) {
            is ApiResponse.Success -> {
                val users = response.data.map {
                    DataMapper.userResponseToUser(it)
                }
                emit(Resource.Success(users))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(emptyList()))
                emit(Resource.Error("User not found"))
            }
        }
    }

    override fun getDetailUser(username: String?): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getDetailUser(username).first()) {
            is ApiResponse.Success -> {
                val user = DataMapper.userResponseToUser(response.data)
                emit(Resource.Success(user))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun getUserFollowers(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getUserFollowers(username).first()) {
            is ApiResponse.Success -> {
                val followers = response.data.map { DataMapper.followResponsetoUser(it) }
                emit(Resource.Success(followers))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun getUserFollowing(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getUserFollowing(username).first()) {
            is ApiResponse.Success -> {
                val following = response.data.map { DataMapper.followResponsetoUser(it) }
                emit(Resource.Success(following))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun getFavoritedUsers(): Flow<List<User>> = localDataSource.getFavoritedUsers().map {
        DataMapper.userEntitytoUser(it)
    }

    override suspend fun setFavoriteUsers(user: User, state: Boolean) {
        val userEntity = DataMapper.usertoUserEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavoriteUsers(userEntity, state) }
    }

    override fun getThemeSetting(): Flow<Boolean> = localDataSource.getThemeSetting()

    override suspend fun setThemeSetting(newSetting: Boolean): Unit =
        localDataSource.setThemeSetting(newSetting)

}
