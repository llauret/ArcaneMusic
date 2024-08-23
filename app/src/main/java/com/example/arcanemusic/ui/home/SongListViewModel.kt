package com.example.arcanemusic.ui.home

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.media.MediaPlayerManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongListViewModel(
    application: Application,
    private val contentResolver: ContentResolver,
) : AndroidViewModel(application) {

    private val _musicList = MutableStateFlow(HomeUiState())
    val musicList: StateFlow<HomeUiState> = _musicList

    init {
        getSongs()
    }

    private fun getSongs() {
        viewModelScope.launch {
            val musicMutableList: MutableList<Music> = withContext(Dispatchers.IO) {
                val tempList = mutableListOf<Music>()
                val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                val projection = arrayOf(
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.DURATION
                )

                val cursor = contentResolver.query(musicUri, projection, null, null, null)

                cursor?.use {
                    val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                    while (it.moveToNext()) {
                        val id = it.getInt(idColumn)
                        val title = it.getString(titleColumn)
                        val artist = it.getString(artistColumn)
                        val data = it.getString(dataColumn)
                        val duration =
                            MediaPlayerManager.convertMsToSeconds(it.getInt(durationColumn))

                        val music = Music(id, title, artist, data, duration)
                        tempList.add(music)
                    }
                }
                tempList
            }

            _musicList.value = HomeUiState(musicMutableList)
        }
    }

    fun playSong(music: Music) {
        val context = getApplication<Application>().applicationContext
        val musicUri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, music.idColumn.toLong()
        )

        MediaPlayerManager.play(context, musicUri)
        Log.i("SongListViewModel", "playSong: ${music.titleColumn}")
    }

}

data class HomeUiState(
    val musicList: MutableList<Music> = mutableListOf()
)