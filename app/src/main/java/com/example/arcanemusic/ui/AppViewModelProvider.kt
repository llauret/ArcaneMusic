package com.example.arcanemusic.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.ui.home.SongListViewModel
import com.example.arcanemusic.ui.song.SongPlayerViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = arcaneApplication()
            SongListViewModel(app, app.contentResolver)
        }
        initializer {
            val app = arcaneApplication()
            SongPlayerViewModel(app, app.contentResolver)
        }
    }
}

fun CreationExtras.arcaneApplication(): ArcaneApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ArcaneApplication)