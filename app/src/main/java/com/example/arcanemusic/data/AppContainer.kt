package com.example.arcanemusic.data

import android.content.Context

interface AppContainer {
    val musicRepository: MusicRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val musicRepository: MusicRepository by lazy {
        OfflineMusicRepository(MusicDatabase.getDataBase(context).musicDAO())
    }
}