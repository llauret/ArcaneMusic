package com.example.arcanemusic.ui.song

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    songPlayerViewModel: SongPlayerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedMusic by songPlayerViewModel.selectedMusic.collectAsState()

    Log.i("SelectedMusic", "Selected music : $selectedMusic")

    selectedMusic?.let { music ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SongMainScreen(modifier = Modifier.weight(1f))
                SongBottomBar(
                    music = music,
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
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
            songPlayerViewModel = songPlayerViewModel
        )
    }
}
