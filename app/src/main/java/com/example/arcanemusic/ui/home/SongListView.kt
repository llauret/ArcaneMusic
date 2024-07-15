package com.example.arcanemusic.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arcanemusic.R
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.navigation.NavigationDestination
import com.example.arcanemusic.ui.AppViewModelProvider

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SongListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.musicList.collectAsState()
    val musicList = uiState.musicList
    MusicList(
        musicList = musicList, modifier = modifier
    )
}

@Composable
private fun MusicList(
    musicList: List<Music>, modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(musicList) { music ->
            SongCard(
                music = music, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SongCard(
    music: Music,
    modifier: Modifier = Modifier,
    viewModel: SongListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { viewModel.playSong(music) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = music.titleColumn,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = music.artistColumn, style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = music.dataColumn, style = MaterialTheme.typography.titleMedium
            )
        }
    }
}