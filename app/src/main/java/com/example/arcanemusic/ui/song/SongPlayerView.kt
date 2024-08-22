package com.example.arcanemusic.ui.song

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arcanemusic.R
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.navigation.NavigationDestination
import com.example.arcanemusic.ui.AppViewModelProvider

object SongPlayerDestination : NavigationDestination {
    override val route = "songPlayer"
    override val titleRes = R.string.app_name
}

@Composable
fun SongPlayer(
    viewModel: SongPlayerViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val selectedMusic by viewModel.selectedMusic.collectAsState()
    Log.i("SelectedMusic", "Selected music : $selectedMusic")

    selectedMusic?.let { music ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SongMainScreen(modifier = Modifier.weight(1f))
                SongBottomBar(music = music)
            }
        }
    }
}

@Composable
fun SongMainScreen(
    modifier: Modifier = Modifier
) {
    val songImage = painterResource(R.mipmap.arcane_icon_round)
    Card(modifier = modifier.padding(16.dp), onClick = { /*TODO*/ }) {
        Image(painter = songImage, contentDescription = "Song image")
    }
}

@Composable
fun SongBottomBar(
    music: Music, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SongCurrentlyPlayed(music = music)
        SongControllerButtons(music = music)
    }
}

@Composable
fun SongCurrentlyPlayed(music: Music) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = music.titleColumn,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = music.artistColumn, style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun SongControllerButtons(
    music: Music,
    songPlayerViewModel: SongPlayerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val playButton = painterResource(R.mipmap.playbutton)
    val pauseButton = painterResource(R.mipmap.pause)
    val shuffle = painterResource(R.mipmap.shuffle)
    val repeat = painterResource(R.mipmap.repeat)
    val skipForward = painterResource(R.mipmap.forward)
    val skipBackward = painterResource(R.mipmap.backward)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = shuffle,
            contentDescription = "Shuffle button",
            modifier = Modifier
                .size(50.dp)
                .clickable(onClick = { /*TODO*/ })
        )
        Image(
            painter = skipBackward,
            contentDescription = "Skip backward button",
            modifier = Modifier.size(50.dp)
        )
        Image(
            painter = playButton,
            contentDescription = "Play button",
            modifier = Modifier
                .size(50.dp)
                .clickable(onClick = {
                    songPlayerViewModel.pausePlaySong(music)
                })
        )
        Image(
            painter = pauseButton,
            contentDescription = "Pause button",
            modifier = Modifier.size(50.dp)
        )
        Image(
            painter = skipForward,
            contentDescription = "Skip forward button",
            modifier = Modifier.size(50.dp)
        )
        Image(
            painter = repeat, contentDescription = "repeat button", modifier = Modifier.size(50.dp)
        )
    }
}