package com.example.arcanemusic.data

import FavoritePlaylist
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDAO {

    @Query("SELECT * FROM music ORDER BY title ASC")
    fun getAllMusic(): Flow<MutableList<Music>>

    @Insert
    suspend fun addToFavoritePlaylist(favoritePlaylist: FavoritePlaylist)

    @Query("SELECT * FROM music INNER JOIN favorite_playlist ON music.id = favorite_playlist.musicId")
    suspend fun getFavoritePlaylist(): List<Music>

}