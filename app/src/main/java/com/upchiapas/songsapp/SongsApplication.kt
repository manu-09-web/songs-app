package com.upchiapas.songsapp

import android.app.Application
import com.upchiapas.songsapp.core.di.AppContainer

class SongsApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
