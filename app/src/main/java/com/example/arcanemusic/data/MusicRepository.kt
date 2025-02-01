package com.example.arcanemusic.data

import FavoritePlaylist
import com.example.arcanemusic.ui.home.HomeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface MusicRepository {
    fun getAllMusic(): Flow<MutableList<Music>>
    suspend fun addToFavoritePlaylist(favorite: FavoritePlaylist)
}

object MusicRepositoryObject {
    private val _musicList = MutableStateFlow(HomeUiState())
    val musicList: StateFlow<HomeUiState> = _musicList

    fun updateMusicList(newList: List<Music>) {
        _musicList.value = HomeUiState(newList.toMutableList())
    }

    fun addToFavoritePlaylist(favorite: FavoritePlaylist) {

    }
}