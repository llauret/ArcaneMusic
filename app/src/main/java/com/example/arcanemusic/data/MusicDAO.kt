package com.example.arcanemusic.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDAO {

    @Query("SELECT * FROM music ORDER BY titleColumn ASC")
    fun getAllMusic(): Flow<MutableList<Music>>

}