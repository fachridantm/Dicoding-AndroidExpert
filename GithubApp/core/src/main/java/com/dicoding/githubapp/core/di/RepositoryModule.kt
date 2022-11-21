package com.dicoding.githubapp.core.di

import com.dicoding.githubapp.core.data.repository.UserRepository
import com.dicoding.githubapp.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideUserRepository(githubRepository: UserRepository): IUserRepository
}