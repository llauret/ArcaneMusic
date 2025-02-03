package com.example.arcanemusic.ui.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arcanemusic.data.Music

@Composable
fun FavoritePlaylist(viewModel: FavoritePlaylistViewModel = viewModel()) {
    val favoriteSongs by viewModel.favoritePlaylist.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(favoriteSongs) { song ->
            FavoriteSongItem(song)
        }
    }
}

@Composable
fun FavoriteSongItem(song: Music) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = song.title)
            Text(text = song.artist)
        }
    }
}