package com.example.arcanemusic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_playlists")
data class FavoritePlaylist(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)