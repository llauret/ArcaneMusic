package com.example.arcanemusic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table pour la siquemu

@Entity(tableName = "music")
data class Music(
    @PrimaryKey(autoGenerate = true) val idColumn: Int = 0,
    val titleColumn: String,
    val artistColumn: String,
    val dataColumn: String,
    val durationColumn: String
)