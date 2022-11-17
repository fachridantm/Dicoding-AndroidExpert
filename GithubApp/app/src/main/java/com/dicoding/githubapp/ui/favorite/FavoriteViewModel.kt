package com.dicoding.githubapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapp.data.UserRepository
import com.dicoding.githubapp.data.model.entity.UserEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UserRepository) : ViewModel() {

    fun getFavoritedUser() = repository.getFavoritedUser()

    fun saveDeleteUser(user: UserEntity, isFavorited: Boolean) {
        viewModelScope.launch {
            if (isFavorited) {
                repository.deleteFavoriteUser(user, false)
            } else {
                repository.addFavoriteUser(user, true)
            }
        }
    }
}