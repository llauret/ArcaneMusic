package com.example.arcanemusic.ui.home

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanemusic.data.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongListViewModel(
    private val contentResolver: ContentResolver,
) : ViewModel() {

    private val _musicList = MutableStateFlow(HomeUiState())
    val musicList: StateFlow<HomeUiState> = _musicList

    init {
        getSongs()
    }

    private fun getSongs() {
        viewModelScope.launch {
            val musicMutableList: MutableList<Music> = mutableListOf()

            val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
            )

            val cursor = contentResolver.query(musicUri, projection, null, null, null)

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

                while (it.moveToNext()) {
                    val id = it.getInt(idColumn)
                    val title = it.getString(titleColumn)
                    val artist = it.getString(artistColumn)
                    val data = it.getString(dataColumn)

                    val music = Music(id, title, artist, data)

                    musicMutableList.add(music)
                }
            }

            _musicList.value = HomeUiState(musicMutableList)
        }
    }

    fun playSong(music: Music) {
        
    }

}

data class HomeUiState(
    val musicList: MutableList<Music> = mutableListOf()
)