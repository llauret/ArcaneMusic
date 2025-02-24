package com.example.arcanemusic.ui.song

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcanemusic.ArcaneApplication
import com.example.arcanemusic.data.FavoritePlaylist
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.data.MusicDatabase
import com.example.arcanemusic.data.MusicRepositoryObject
import com.example.arcanemusic.data.OfflineMusicRepository
import com.example.arcanemusic.media.MediaPlayerManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongPlayerViewModel(
    application: ArcaneApplication,
    private val contentResolver: ContentResolver,
    private val offlineMusicRepository: OfflineMusicRepository = OfflineMusicRepository(
        MusicDatabase.getDataBase(application).musicDAO()
    )
) : AndroidViewModel(application) {

    private val _selectedMusic = MutableStateFlow<Music?>(null)
    val selectedMusic: StateFlow<Music?> = _selectedMusic

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _isOnRepeat = MutableStateFlow(false)
    val isOnRepeat: StateFlow<Boolean> = _isOnRepeat

    private val _isOnShuffle = MutableStateFlow(false)
    val isOnShuffle: StateFlow<Boolean> = _isOnShuffle

    fun setSelectedMusic(music: Music) {
        Log.i("SongListViewModel", "setSelectedMusic: ${music.title}")
        _selectedMusic.value = music
    }

    private fun setSongIndex(index: Int) {
        Log.i("SongListViewModel", "setSongIndex: $index")
        _currentIndex.value = index
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

    fun skipForward() {
        val current = _currentIndex.value
        Log.i("SongListViewModel", "Current index in skipForward: $current")
        if (current == -1) return

        val nextIndex = current + 1
        val songs = MusicRepositoryObject.musicList.value.musicList

        Log.i("SongListViewModel", "Next index: $nextIndex, Songs size: ${songs.size}")
        if (nextIndex < songs.size) {
            playSongAtIndex(nextIndex)
        }
    }

    fun skipBackward() {
        val current = _currentIndex.value
        Log.i("SongListViewModel", "Current index in skipBackward: $current")
        if (current == -1) return
        if (MediaPlayerManager.getCurrentPosition()!! > 5000) {
            MediaPlayerManager.seekTo(0)
            return
        }
        val prevIndex = current - 1
        if (prevIndex >= 0) {
            playSongAtIndex(prevIndex)
        }
    }

    fun repeatSong() {
        Log.i("SongListViewModel", "Repeat song")
        _isOnRepeat.value = !_isOnRepeat.value
        val current = _currentIndex.value
        if (current == -1) return

        viewModelScope.launch {
            while (isOnRepeat.value) {
                if (MediaPlayerManager.isEndOfSong()) {
                    playSongAtIndex(current)
                }
                delay(1000)
            }
        }
    }

    fun shuffleSongs() {
        Log.i("SongListViewModel", "Shuffle songs")
        _isOnShuffle.value = !_isOnShuffle.value
        Log.i("SongListViewModel", "Is on shuffle: ${_isOnShuffle.value}")

        viewModelScope.launch {
            while (_isOnShuffle.value) {
                if (MediaPlayerManager.isEndOfSong()) {
                    val randomIndex =
                        MusicRepositoryObject.musicList.value.musicList.indices.random()
                    Log.i("SongListViewModel", "Random index: $randomIndex")
                    playSongAtIndex(randomIndex)
                }
                delay(1000)
            }
        }
    }

    fun playSong(music: Music) {
        val index =
            MusicRepositoryObject.musicList.value.musicList.indexOfFirst { it.id == music.id }
        Log.i("SongListViewModel", "playSong: ${music.title} at index $index")
        if (index != -1) {
            playSongAtIndex(index)
            setSelectedMusic(music)
            setSongIndex(index)
        }
    }

    private fun playSongAtIndex(index: Int) {
        val songs = MusicRepositoryObject.musicList.value.musicList
        if (index in songs.indices) {
            val music = songs[index]
            val context = getApplication<Application>().applicationContext
            val musicUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, music.id.toLong()
            )

            MediaPlayerManager.play(context, musicUri) {
                skipForward()
            }
            Log.i("SongListViewModel", "playSongAtIndex: ${music.title} at index $index")

            _currentIndex.value = index
            Log.i("SongListViewModel", "Current index: ${_currentIndex.value}")
            _selectedMusic.value = music
        }
    }

    fun addToFavoritePlaylist(music: Music) {
        viewModelScope.launch {
            val favorite = FavoritePlaylist(id = System.currentTimeMillis(), musicId = music.id)
            Log.i("SongListViewModel", "Adding to favorite playlist: $favorite")
            offlineMusicRepository.addToFavoritePlaylist(favorite)
            Log.i("SongListViewModel", "Added to favorite playlist")
        }
    }

    fun getSongDuration(): Int? {
        return MediaPlayerManager.getSongDuration()
    }
}

data class HomeUiState(
    val musicList: MutableList<Music> = mutableListOf()
)