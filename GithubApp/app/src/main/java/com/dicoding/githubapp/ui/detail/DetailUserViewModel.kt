package com.dicoding.githubapp.ui.detail

import android.content.Context
import androidx.lifecycle.*
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import com.dicoding.githubapp.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _hasFavorite = MutableLiveData(false)
    val hasFavorite: LiveData<Boolean> = _hasFavorite

    fun setFavorite(user: User) {
        _user.value = user
        _isFavorite.value = user.isFavorited
        _hasFavorite.value = true
    }

    fun setFavoriteUsers(user: User, state: Boolean, context: Context) =
        viewModelScope.launch {
            userUseCase.setFavoriteUsers(user, state)
            _user.value = user
            _isFavorite.value = state
            Event(
                if (state) {
                    context.getString(R.string.add_favorite, user.username)
                } else {
                    context.getString(R.string.remove_favorite, user.username)
                }
            ).also { _toast.value = it }
        }

    fun getDetailUser(username: String?) = userUseCase.getDetailUser(username).asLiveData()

    fun getUserFollowers(username: String) = userUseCase.getUserFollowers(username).asLiveData()

    fun getUserFollowing(username: String) = userUseCase.getUserFollowing(username).asLiveData()


}