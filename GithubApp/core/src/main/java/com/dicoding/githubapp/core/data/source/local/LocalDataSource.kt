package com.dicoding.githubapp.core.data.source.local

import com.dicoding.githubapp.core.data.source.local.datastore.SettingPreferences
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val settingPreferences: SettingPreferences,
    private val userDao: UserDao
) {
    suspend fun setThemeSetting(newSetting: Boolean) = settingPreferences.setThemeSetting(newSetting)

    fun getThemeSetting(): Flow<Boolean> = settingPreferences.getThemeSetting()

    fun getFavoritedUsers(): Flow<List<UserEntity>> = userDao.getFavoritedUsers()
    fun setFavoriteUsers(user: UserEntity, newState: Boolean) {
        if (newState) {
            user.isFavorited = newState
            userDao.apply {
                insertUser(user)
                updateUser(user)
            }
        } else {
            userDao.deleteUser(user)
        }
    }
}