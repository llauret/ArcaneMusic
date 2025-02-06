package com.example.arcanemusic.ui.artist

import android.content.ContentResolver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.data.ArtistSongCount
import com.example.arcanemusic.data.MusicDatabase
import com.example.arcanemusic.data.OfflineMusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistViewModel(
    application: ArcaneApplication,
    private val contentResolver: ContentResolver,
    private val offlineMusicRepository: OfflineMusicRepository = OfflineMusicRepository(
        MusicDatabase.getDataBase(application).musicDAO()
    )
) : AndroidViewModel(application) {

    private val _artistList = MutableStateFlow<List<ArtistSongCount>>(emptyList())
    val artistList: StateFlow<List<ArtistSongCount>> = _artistList

    init {
        viewModelScope.launch {
            offlineMusicRepository.getArtists().collect { artists ->
                _artistList.value = artists
            }
        }
    }
}