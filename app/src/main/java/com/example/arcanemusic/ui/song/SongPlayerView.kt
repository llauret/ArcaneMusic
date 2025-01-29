package com.example.arcanemusic.ui.song

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arcanemusic.R
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.media.MediaPlayerManager
import com.example.arcanemusic.navigation.NavigationDestination
import com.example.arcanemusic.ui.AppViewModelProvider
import com.example.arcanemusic.ui.home.SongListViewModel
import kotlinx.coroutines.delay

object SongPlayerDestination : NavigationDestination {
    override val route = "songPlayer"
    override val titleRes = R.string.app_name
}

@Composable
fun SongPlayer(
    songPlayerViewModel: SongPlayerViewModel = viewModel(factory = AppViewModelProvider.Factory),
    songListViewModel: SongListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedMusic by songPlayerViewModel.selectedMusic.collectAsState()

    Log.i("SelectedMusic", "Selected music : $selectedMusic")

    selectedMusic?.let { music ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SongMainScreen(modifier = Modifier.weight(1f))
                SongBottomBar(
                    music = music,
                    songListViewModel = songListViewModel,
                    songPlayerViewModel = songPlayerViewModel
                )
            }
        }
    }
}

@Composable
fun SongMainScreen(
    modifier: Modifier = Modifier
) {
    val songImage = painterResource(R.mipmap.arcane_icon_round)

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { /* TODO: Action au clic */ }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = songImage,
                contentDescription = "Song image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun SongBottomBar(
    music: Music,
    modifier: Modifier = Modifier,
    songListViewModel: SongListViewModel,
    songPlayerViewModel: SongPlayerViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SongProgressBar()
        SongCurrentlyPlayed(music = music)
        SongControllerButtons(
            music = music,
            songListViewModel = songListViewModel,
            songPlayerViewModel = songPlayerViewModel
        )
    }
}

@Composable
fun SongCurrentlyPlayed(music: Music) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { /*TODO*/ },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Cover Art",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = music.titleColumn,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = music.artistColumn,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun SongProgressBar() {
    val totalDuration = MediaPlayerManager.getSongDuration() ?: 1
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var currentTime by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            val currentPosition = MediaPlayerManager.getCurrentPosition() ?: 0
            currentProgress = currentPosition.toFloat() / totalDuration
            currentTime = currentPosition
            delay(500)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentTime),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = formatTime(totalDuration),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(currentProgress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
            )
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatTime(millis: Int): String {
    val minutes = (millis / 1000) / 60
    val seconds = (millis / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}


@Composable
fun SongControllerButtons(
    songPlayerViewModel: SongPlayerViewModel,
    songListViewModel: SongListViewModel,
    music: Music
) {
    val playButton = painterResource(R.mipmap.playbutton)
    val pauseButton = painterResource(R.mipmap.pause)
    val shuffle = painterResource(R.mipmap.shuffle)
    val repeat = painterResource(R.mipmap.repeat)
    val skipForward = painterResource(R.mipmap.forward)
    val skipBackward = painterResource(R.mipmap.backward)

    val isPlaying by songPlayerViewModel.isPlaying.collectAsState()

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
            onClick = { /* TODO: Shuffle functionality */ },
            modifier = Modifier
                .size(60.dp)
                .background(Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = shuffle,
                contentDescription = "Shuffle button"
            )
        }

        IconButton(
            onClick = { /* TODO: Skip backward functionality */ },
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
                songListViewModel.skipForward()
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
            onClick = { /* TODO: Repeat functionality */ },
            modifier = Modifier
                .size(60.dp)
                .background(Color.Transparent, shape = CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = repeat,
                contentDescription = "Repeat button"
            )
        }
    }
}

