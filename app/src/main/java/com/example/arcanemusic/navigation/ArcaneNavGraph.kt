package com.example.arcanemusic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arcanemusic.ui.AppViewModelProvider
import com.example.arcanemusic.ui.artist.ArtistView
import com.example.arcanemusic.ui.artist.ArtistViewModel
import com.example.arcanemusic.ui.home.HomeDestination
import com.example.arcanemusic.ui.home.HomeScreen
import com.example.arcanemusic.ui.home.SongListViewModel
import com.example.arcanemusic.ui.nav.NavigationBarItem
import com.example.arcanemusic.ui.playlist.FavoritePlaylist
import com.example.arcanemusic.ui.playlist.FavoritePlaylistViewModel
import com.example.arcanemusic.ui.song.SongPlayer
import com.example.arcanemusic.ui.song.SongPlayerDestination
import com.example.arcanemusic.ui.song.SongPlayerViewModel

@Composable
fun ArcaneNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val songPlayerViewModel: SongPlayerViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val songListViewModel: SongListViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val favoritePlaylistViewModel: FavoritePlaylistViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
    val artistViewModel: ArtistViewModel = viewModel(factory = AppViewModelProvider.Factory)
    NavHost(
        navController = navController, startDestination = HomeDestination.route, modifier = modifier
    ) {
        composable(route = NavigationBarItem.Home.route) {
            HomeScreen(
                songPlayerViewModel = songPlayerViewModel,
                songListViewModel = songListViewModel,
                navController = navController,
            )
        }
        composable(route = SongPlayerDestination.route) {
            SongPlayer(
                songPlayerViewModel = songPlayerViewModel
            )
        }
        composable(route = NavigationBarItem.Playlist.route) {
            FavoritePlaylist(favoritePlaylistViewModel)
        }
        composable(route = NavigationBarItem.Artist.route) {
            ArtistView(artistViewModel)
        }
    }
}
