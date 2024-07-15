package com.example.arcanemusic.data

import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getAllMusic(): Flow<MutableList<Music>>
}