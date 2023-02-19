package com.dicoding.githubapp.di

import com.dicoding.githubapp.core.domain.usecase.UserInteractor
import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModule {
    fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase
}