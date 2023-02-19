package com.dicoding.githubapp.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import com.dicoding.githubapp.favorite.ui.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val userUseCase: UserUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(userUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}