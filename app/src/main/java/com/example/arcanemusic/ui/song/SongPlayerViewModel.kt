package com.example.arcanemusic.ui.song

import android.content.ContentResolver
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.media.MediaPlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SongPlayerViewModel(
    application: ArcaneApplication, private val contentResolver: ContentResolver
) : AndroidViewModel(application) {

    private val _selectedMusic = MutableStateFlow<Music?>(null)
    val selectedMusic: StateFlow<Music?> = _selectedMusic


    init {
        Log.i("SongPlayerViewModel", "SongPlayerViewModel created")
    }

    fun setSelectedMusic(music: Music) {
        _selectedMusic.value = music
        Log.i(
            "SongPlayerViewModel", "Selected music: $music :   Variable : ${_selectedMusic.value}"
        )
    }

    fun pausePlaySong(music: Music) {
        if (MediaPlayerManager.isPlaying()) {
            Log.i("SongPlayerViewModel", "Song is playing, pausing")
            MediaPlayerManager.pause()
        } else {
            Log.i("SongPlayerViewModel", "Song is paused, resuming")
            MediaPlayerManager.resume()
        }
        Log.i("SongPlayerViewModel", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG song : $music")
    }
}