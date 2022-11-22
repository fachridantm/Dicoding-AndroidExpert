package com.dicoding.githubapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun getDetailUser(username: String?) = userUseCase.getDetailUser(username).asLiveData()

    fun getUserFollowers(username: String) = userUseCase.getUserFollowers(username).asLiveData()

    fun getUserFollowing(username: String) = userUseCase.getUserFollowing(username).asLiveData()
}