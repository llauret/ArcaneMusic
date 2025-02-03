package com.example.arcanemusic.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.data.MusicDatabase
import com.example.arcanemusic.data.OfflineMusicRepository
import com.example.arcanemusic.ui.home.SongListViewModel
import com.example.arcanemusic.ui.playlist.FavoritePlaylistViewModel
import com.example.arcanemusic.ui.song.SongPlayerViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = arcaneApplication()
            val musicDatabase = MusicDatabase.getDataBase(app)
            val offlineMusicRepository = OfflineMusicRepository(musicDatabase.musicDAO())
            SongListViewModel(app, app.contentResolver, offlineMusicRepository)
        }
        initializer {
            val app = arcaneApplication()
            val musicDatabase = MusicDatabase.getDataBase(app)
            val offlineMusicRepository = OfflineMusicRepository(musicDatabase.musicDAO())
            SongPlayerViewModel(app, app.contentResolver, offlineMusicRepository)
        }
        initializer {
            val app = arcaneApplication()
            val musicDatabase = MusicDatabase.getDataBase(app)
            val offlineMusicRepository = OfflineMusicRepository(musicDatabase.musicDAO())
            FavoritePlaylistViewModel(app, app.contentResolver, offlineMusicRepository)
        }
    }
}

fun CreationExtras.arcaneApplication(): ArcaneApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ArcaneApplication)