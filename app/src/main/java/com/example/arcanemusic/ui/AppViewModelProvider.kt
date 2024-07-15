package com.example.arcanemusic.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.ui.home.SongListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = arcaneApplication()
            SongListViewModel(app.contentResolver)
        }
    }
}

fun CreationExtras.arcaneApplication(): ArcaneApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ArcaneApplication)