package com.dicoding.githubapp.ui.detail

import androidx.lifecycle.*
import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.domain.model.Follow
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _result = MutableLiveData<Resource<List<Follow>>>()
    val result: LiveData<Resource<List<Follow>>> = _result

    fun getDetailUser(username: String?) = userUseCase.getDetailUser(username).asLiveData()

    fun getUserFollowers(username: String) {
        viewModelScope.launch {
            userUseCase.getUserFollowers(username).collect {
                _result.value = it
            }
        }
    }

    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            userUseCase.getUserFollowing(username).collect {
                _result.value = it
            }
        }
    }
}