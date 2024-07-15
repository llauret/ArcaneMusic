package com.example.arcanemusic.data

import kotlinx.coroutines.flow.Flow


class OfflineMusicRepository(private val musicDAO: MusicDAO) : MusicRepository {

    override fun getAllMusic(): Flow<MutableList<Music>> = musicDAO.getAllMusic()

}