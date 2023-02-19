package com.dicoding.githubapp.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getFavoritedUsers() = userUseCase.getFavoritedUsers().asLiveData()
}