package com.dicoding.githubapp.core.data.source.local.room

import androidx.room.*
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserItem where favorite = 1")
    fun getFavoritedUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM UserItem WHERE login = :username AND favorite = 1)")
    suspend fun isUserFavorited(username: String): Boolean
}