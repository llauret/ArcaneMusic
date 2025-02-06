package com.example.arcanemusic.data

import com.example.arcanemusic.ui.home.HomeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface MusicRepository {
    fun getAllMusic(): Flow<MutableList<Music>>
    suspend fun getArtists(): Flow<List<ArtistSongCount>>
    suspend fun addToFavoritePlaylist(favorite: FavoritePlaylist)
    suspend fun insertMusic(music: Music)
    suspend fun getFavoritePlaylist(): Flow<List<Music>>
}

object MusicRepositoryObject {
    private val _musicList = MutableStateFlow(HomeUiState())
    val musicList: StateFlow<HomeUiState> = _musicList

    fun insertMusic(newList: List<Music>) {
        _musicList.value = HomeUiState(newList.toMutableList())
    }

    fun addToFavoritePlaylist(favorite: FavoritePlaylist) {

    }
}