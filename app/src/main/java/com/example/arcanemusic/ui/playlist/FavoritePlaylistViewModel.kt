package com.example.arcanemusic.ui.playlist

import android.content.ContentResolver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.data.MusicDatabase
import com.example.arcanemusic.data.OfflineMusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritePlaylistViewModel(
    application: ArcaneApplication,
    private val contentResolver: ContentResolver,
    private val offlineMusicRepository: OfflineMusicRepository = OfflineMusicRepository(
        MusicDatabase.getDataBase(application).musicDAO()
    )
) : AndroidViewModel(application) {

    private val _favoritePlaylist = MutableStateFlow<List<Music>>(emptyList())
    val favoritePlaylist: StateFlow<List<Music>> = _favoritePlaylist

    init {
        viewModelScope.launch {
            offlineMusicRepository.getFavoritePlaylist().collect { songs ->
                _favoritePlaylist.value = songs
            }
        }
    }
}