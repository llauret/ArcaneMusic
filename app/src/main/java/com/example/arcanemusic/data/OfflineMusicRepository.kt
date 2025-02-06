package com.example.arcanemusic.data

import kotlinx.coroutines.flow.Flow


class OfflineMusicRepository(private val musicDAO: MusicDAO) : MusicRepository {

    override suspend fun insertMusic(music: Music) {
        musicDAO.insertMusic(music)
    }

    override fun getAllMusic(): Flow<MutableList<Music>> = musicDAO.getAllMusic()

    override suspend fun addToFavoritePlaylist(favorite: FavoritePlaylist) {
        musicDAO.addToFavoritePlaylist(favorite)
    }

    override suspend fun getFavoritePlaylist(): Flow<List<Music>> =
        musicDAO.getFavoritePlaylist()

    override suspend fun getArtists(): Flow<List<ArtistSongCount>> =
        musicDAO.getArtists()
}