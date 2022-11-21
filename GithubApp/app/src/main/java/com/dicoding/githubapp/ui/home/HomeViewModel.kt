package com.dicoding.githubapp.ui.home

import androidx.lifecycle.*
import com.dicoding.githubapp.core.data.source.Resource
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _result = MutableLiveData<Resource<List<User>>>()
    val result: LiveData<Resource<List<User>>> = _result

    fun searchUser(query: String) {
        viewModelScope.launch {
            userUseCase.searchUser(query).collect { result ->
                _result.value = result
            }
        }
    }

    fun getThemeSetting(): LiveData<Boolean> = userUseCase.getThemeSetting().asLiveData()

    fun saveThemeSetting(newSetting: Boolean) {
        viewModelScope.launch {
            userUseCase.setThemeSetting(newSetting)
        }
    }
}