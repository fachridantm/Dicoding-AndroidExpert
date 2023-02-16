package com.dicoding.githubapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubapp.core.data.source.local.entity.UserEntity
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun getFavoritedUsers() = userUseCase.getFavoritedUsers().asLiveData()

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            userUseCase.insertUser(user)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            userUseCase.deleteUser(user)
        }
    }
}