package com.example.arcanemusic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Music::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDAO(): MusicDAO

    companion object {
        @Volatile
        private var Instance: MusicDatabase? = null

        fun getDataBase(context: Context): MusicDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MusicDatabase::class.java, "music_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}