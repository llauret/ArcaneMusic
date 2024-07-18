package com.example.arcanemusic.ui.home

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.arcanemusic.R
import com.example.arcanemusic.data.Music
import com.example.arcanemusic.navigation.NavigationDestination
import com.example.arcanemusic.ui.AppViewModelProvider
import com.example.arcanemusic.ui.song.SongPlayerDestination
import com.example.arcanemusic.ui.song.SongPlayerViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SongListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    songPlayerViewModel: SongPlayerViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val uiState by viewModel.musicList.collectAsState()
    val musicList = uiState.musicList
    MusicList(musicList = musicList, modifier = modifier, onSongClicked = { music ->
        songPlayerViewModel.setSelectedMusic(music)
        navController.navigate(SongPlayerDestination.route)
        Log.i("HomeScreen", "Navigating to SongPlayer with : $music")
    }, playSong = { music ->
        viewModel.playSong(music)
    })

}

@Composable
private fun MusicList(
    musicList: List<Music>,
    modifier: Modifier = Modifier,
    onSongClicked: (Music) -> Unit,
    playSong: (Music) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(musicList) { music ->
            SongCard(music = music, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    onSongClicked(music)
                    playSong(music)
                })
        }
    }
}

@Composable
fun SongCard(
    music: Music, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 2.dp, color = Color.Red, shape = RoundedCornerShape(10.dp)
            )
            .width(50.dp)
            .height(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = music.titleColumn, style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp
                    )
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = music.artistColumn,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}