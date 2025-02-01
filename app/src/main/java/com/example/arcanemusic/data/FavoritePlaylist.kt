package com.example.arcanemusic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_playlist")
data class FavoritePlaylist(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val musicId: Int
)