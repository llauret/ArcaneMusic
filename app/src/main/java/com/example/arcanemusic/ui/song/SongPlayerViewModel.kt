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

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    init {
        Log.i("SongPlayerViewModel", "SongPlayerViewModel created")
    }

    fun setSelectedMusic(music: Music) {
        _selectedMusic.value = music
        Log.i(
            "SongPlayerViewModel", "Selected music: $music :   Variable : ${_selectedMusic.value}"
        )
    }

    fun pausePlaySong() {
        if (MediaPlayerManager.isPlaying()) {
            MediaPlayerManager.pause()
            _isPlaying.value = false
        } else {
            MediaPlayerManager.resume()
            _isPlaying.value = true
        }
    }

    fun getSongDuration(): Int? {
        return MediaPlayerManager.getSongDuration()
    }
}