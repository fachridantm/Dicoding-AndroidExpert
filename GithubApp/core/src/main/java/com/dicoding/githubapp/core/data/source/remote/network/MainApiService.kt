package com.dicoding.githubapp.core.data.source.remote.network

import com.dicoding.githubapp.core.data.source.remote.response.FollowItem
import com.dicoding.githubapp.core.data.source.remote.response.UserItem
import com.dicoding.githubapp.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
    ): UserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username")
        username: String?,
    ): UserItem

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username")
        username: String?,
    ): ArrayList<FollowItem>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username")
        username: String?,
    ): ArrayList<FollowItem>
}