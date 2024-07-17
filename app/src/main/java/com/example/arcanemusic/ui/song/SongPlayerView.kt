package com.example.arcanemusic.ui.song

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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

object SongPlayerDestination : NavigationDestination {
    override val route = "songPlayer"
    override val titleRes = R.string.app_name
}


@Composable
fun SongPlayer(
    modifier: Modifier = Modifier, viewModel: SongPlayerViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val selectedMusic by viewModel.selectedMusic.collectAsState()
    Log.i("SelectedMusic", "Selected music : $selectedMusic")

    selectedMusic?.let { music ->
        SongMainScreen(music = music)
        SongBottomBar(music = music)
    }
}

@Composable
fun SongMainScreen(
    music: Music
) {
    Card(modifier = Modifier.padding(16.dp), onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
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
    }
}

@Composable
fun SongBottomBar(
    music: Music
) {
    Card(modifier = Modifier, onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
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
}