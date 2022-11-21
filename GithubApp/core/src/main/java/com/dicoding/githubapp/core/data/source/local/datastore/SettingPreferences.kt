package com.dicoding.githubapp.core.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun setThemeSetting(isNightModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isNightModeActive
        }
    }

    companion object {
        private val THEME_KEY = booleanPreferencesKey("THEME_KEY")
    }
}