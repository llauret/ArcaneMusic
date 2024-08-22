package com.example.arcanemusic.media

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

object MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null

    fun play(context: Context, uri: Uri) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, uri)
        } else {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(context, uri)
            mediaPlayer?.prepare()
        }
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun resume() {
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }

}