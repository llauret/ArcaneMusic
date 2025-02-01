package com.example.arcanemusic.ui.home

import android.app.Application
import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.data.MusicRepositoryObject
import com.example.arcanemusic.media.MediaPlayerManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongListViewModel(
    application: Application,
    private val contentResolver: ContentResolver,
) : AndroidViewModel(application) {

    val musicList: StateFlow<HomeUiState> = MusicRepositoryObject.musicList

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
                    val id = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val title = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val artist = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val data = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val duration = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                    while (it.moveToNext()) {
                        val id = it.getInt(id)
                        val title = it.getString(title)
                        val artist = it.getString(artist)
                        val data = it.getString(data)
                        val duration =
                            MediaPlayerManager.convertMsToSeconds(it.getInt(duration))

                        val music = Music(id, title, artist, data, duration)
                        tempList.add(music)
                    }
                }
                tempList
            }

            MusicRepositoryObject.updateMusicList(musicMutableList)
        }
    }
}

data class HomeUiState(
    val musicList: MutableList<Music> = mutableListOf()
)