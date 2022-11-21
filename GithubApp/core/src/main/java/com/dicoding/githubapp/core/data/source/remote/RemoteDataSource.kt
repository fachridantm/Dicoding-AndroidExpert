package com.dicoding.githubapp.core.data.source.remote

import com.dicoding.githubapp.core.data.source.remote.network.ApiResponse
import com.dicoding.githubapp.core.data.source.remote.network.MainApiService
import com.dicoding.githubapp.core.data.source.remote.response.FollowItem
import com.dicoding.githubapp.core.data.source.remote.response.UserItem
import com.dicoding.githubapp.core.utils.getErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun searchUser(query: String): Flow<ApiResponse<List<UserItem>>> = flow {
        try {
            val response = mainApiService.searchUser(query)
            val users = response.items
            if (users != null) {
                emit(ApiResponse.Success(users))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> e.getErrorMessage().toString()
                    }
                    emit(ApiResponse.Error(message))
                }
                else -> emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String?): Flow<ApiResponse<UserItem>> = flow {
        try {
            val response = mainApiService.getDetailUser(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> e.getErrorMessage().toString()
                    }
                    emit(ApiResponse.Error(message))
                }
                else -> emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<FollowItem>>> = flow {
        try {
            val response = mainApiService.getUserFollowers(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> e.getErrorMessage().toString()
                    }
                    emit(ApiResponse.Error(message))
                }
                else -> emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<FollowItem>>> = flow {
        try {
            val response = mainApiService.getUserFollowing(username)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val message = when (e.code()) {
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> e.getErrorMessage().toString()
                    }
                    emit(ApiResponse.Error(message))
                }
                else -> emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)
}