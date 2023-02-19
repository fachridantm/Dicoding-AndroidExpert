package com.dicoding.githubapp.favorite.di

import android.content.Context
import com.dicoding.githubapp.di.FavoriteModuleDependencies
import com.dicoding.githubapp.favorite.ui.FavoriteUserActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteUserActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}