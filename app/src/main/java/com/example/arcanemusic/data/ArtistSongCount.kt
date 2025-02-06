package com.example.arcanemusic.data

import androidx.room.ColumnInfo

data class ArtistSongCount(
    @ColumnInfo(name = "artist")
    val artist: String,
    @ColumnInfo(name = "songCount")
    val songCount: Int
)
