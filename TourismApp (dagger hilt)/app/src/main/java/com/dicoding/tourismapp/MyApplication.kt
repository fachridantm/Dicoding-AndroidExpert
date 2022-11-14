package com.dicoding.tourismapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.scopes.ViewModelScoped

@HiltAndroidApp @ViewModelScoped
open class MyApplication : Application()

