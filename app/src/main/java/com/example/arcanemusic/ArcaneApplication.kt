package com.example.arcanemusic

import android.app.Application
import com.example.arcanemusic.data.AppContainer
import com.example.arcanemusic.data.AppDataContainer

class ArcaneApplication : Application() {
    private lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}