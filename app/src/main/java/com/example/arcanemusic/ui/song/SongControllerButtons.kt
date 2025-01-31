package com.example.arcanemusic.ui.song

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.arcanemusic.R
import com.example.arcanemusic.data.Music

@Composable
fun SongControllerButtons(
    songPlayerViewModel: SongPlayerViewModel,
    music: Music
) {
    val playButton = painterResource(R.mipmap.playbutton)
    val pauseButton = painterResource(R.mipmap.pause)
    val shuffle = painterResource(R.mipmap.shuffle)
    val repeat = painterResource(R.mipmap.repeat)
    val skipForward = painterResource(R.mipmap.forward)
    val skipBackward = painterResource(R.mipmap.backward)

    val isPlaying by songPlayerViewModel.isPlaying.collectAsState()
    val isOnRepeat by songPlayerViewModel.isOnRepeat.collectAsState()
    val isOnShuffle by songPlayerViewModel.isOnShuffle.collectAsState()

    val rotation by animateFloatAsState(
        targetValue = if (isPlaying) 360f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = ""
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPlaying) 0.8f else 1f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = ""
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        IconButton(
            onClick = { songPlayerViewModel.shuffleSongs() },
            modifier = Modifier
                .size(60.dp)
                .background(
                    if (isOnShuffle) Color.Gray else Color.Transparent,
                    shape = CircleShape
                )
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = shuffle,
                contentDescription = "Shuffle button"
            )
        }

        IconButton(
            onClick = { songPlayerViewModel.skipBackward() },
            modifier = Modifier
                .size(60.dp)
                .background(Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = skipBackward,
                contentDescription = "Skip backward button"
            )
        }

        IconButton(
            onClick = {
                songPlayerViewModel.pausePlaySong()
            },
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer(rotationZ = rotation, alpha = alpha)
                .background(Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        ) {
            Image(
                painter = if (isPlaying) pauseButton else playButton,
                contentDescription = if (isPlaying) "Pause button" else "Play button"
            )
        }

        IconButton(
            onClick = {
                songPlayerViewModel.setSelectedMusic(music = music)
                songPlayerViewModel.skipForward()
            },
            modifier = Modifier
                .size(60.dp)
                .background(Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = skipForward,
                contentDescription = "Skip forward button"
            )
        }

        IconButton(
            onClick = { songPlayerViewModel.repeatSong() },
            modifier = Modifier
                .size(60.dp)
                .background(if (isOnRepeat) Color.Gray else Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = repeat,
                contentDescription = "Repeat button"
            )
        }
    }
}