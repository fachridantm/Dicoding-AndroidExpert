package com.dicoding.githubapp.favorite.di

import android.content.Context
import com.dicoding.githubapp.di.FavoriteModule
import com.dicoding.githubapp.ui.favorite.FavoriteUserActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModule::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteUserActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModule: FavoriteModule): Builder
        fun build(): FavoriteComponent
    }
}