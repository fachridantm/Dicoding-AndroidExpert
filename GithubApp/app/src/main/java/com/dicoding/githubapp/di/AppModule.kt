package com.dicoding.githubapp.di

import com.dicoding.githubapp.core.domain.usecase.UserUseCase
import com.dicoding.githubapp.core.domain.usecase.UserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideUserUseCase(userUseCaseImpl: UserUseCaseImpl): UserUseCase
}