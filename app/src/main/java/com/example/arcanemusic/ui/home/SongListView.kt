package com.example.arcanemusic.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.arcanemusic.ui.theme.earwigFactory
import com.example.arcanemusic.ui.theme.p5royal

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    songListViewModel: SongListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    songPlayerViewModel: SongPlayerViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val uiState by songListViewModel.musicList.collectAsState()
    val musicList = uiState.musicList
    MusicList(musicList = musicList, modifier = modifier, onSongClicked = { music ->
        songPlayerViewModel.playSong(music)
        navController.navigate(SongPlayerDestination.route)
        Log.i(
            "HomeScreen",
            "Navigating to SongPlayer with : $music"
        )
    }, playSong = { music ->
        songPlayerViewModel.playSong(music)
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
        modifier = modifier.padding(top = 60.dp)
    ) {
        items(musicList) { music ->
            SongCard(music = music, modifier = Modifier.fillMaxWidth(), onClick = {
                onSongClicked(music)
                playSong(music)
            })
        }
    }
}

@Composable
fun SongCard(
    music: Music, modifier: Modifier = Modifier, onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberRipple(bounded = true)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(
                interactionSource = interactionSource, indication = indication, onClick = onClick
            )
    ) {
        Image(
            painter = painterResource(R.drawable.card),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RectangleShape,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = music.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp,
                            fontFamily = p5royal
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .offset(y = 50.dp)
                    )
                    Text(
                        text = music.artist,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.Gray,
                            fontFamily = earwigFactory,
                            fontSize = 25.sp
                        ),
                        modifier = Modifier
                            .rotate(-10f)
                            .offset(y = (-15).dp)
                    )
                }
                Text(
                    text = music.duration,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Gray,
                        fontFamily = p5royal,
                        fontSize = 30.sp
                    ),
                    modifier = Modifier
                        .alignByBaseline()
                        .rotate(-15f)
                        .offset(y = (10).dp)
                        .offset(x = (-25).dp)
                )
            }
        }
    }
}