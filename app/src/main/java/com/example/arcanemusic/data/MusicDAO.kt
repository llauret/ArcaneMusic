package com.example.arcanemusic.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
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

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT artist, COUNT(title) AS songCount FROM music GROUP BY artist")
    fun getArtists(): Flow<List<ArtistSongCount>>
}