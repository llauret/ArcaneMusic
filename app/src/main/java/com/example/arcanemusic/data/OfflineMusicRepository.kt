package com.example.arcanemusic.data

import FavoritePlaylist
import kotlinx.coroutines.flow.Flow


class OfflineMusicRepository(private val musicDAO: MusicDAO) : MusicRepository {

    override fun getAllMusic(): Flow<MutableList<Music>> = musicDAO.getAllMusic()

    override suspend fun addToFavoritePlaylist(favorite: FavoritePlaylist) {
        musicDAO.addToFavoritePlaylist(favorite)
    }

}