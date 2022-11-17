package com.dicoding.githubapp.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.githubapp.data.api.ApiConfig
import com.dicoding.githubapp.data.room.UserDatabase

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    "settings"
)

object Injection {
    fun provideRepository(context: Context): com.dicoding.githubapp.data.UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val preferences = SettingPreferences.getInstance(context.dataStore)
        return com.dicoding.githubapp.data.UserRepository.getInstance(preferences, apiService, dao)
    }
}