package com.example.arcanemusic.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDAO {

    @Query("SELECT * FROM music ORDER BY title ASC")
    fun getAllMusic(): Flow<MutableList<Music>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavoritePlaylist(favoritePlaylist: FavoritePlaylist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(music: Music)

    @Query("SELECT * FROM music INNER JOIN favorite_playlist ON music.id = favorite_playlist.musicId")
    fun getFavoritePlaylist(): Flow<List<Music>>

}