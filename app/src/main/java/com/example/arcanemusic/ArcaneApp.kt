package com.example.arcanemusic

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arcanemusic.navigation.ArcaneNavHost
import com.example.arcanemusic.ui.nav.NavigationBar
import com.example.arcanemusic.ui.song.SongPlayerDestination

@Composable
fun ArcaneApp(navController: NavHostController = rememberNavController()) {
    val currentRoute = navController.currentDestination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != SongPlayerDestination.route) {
                NavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        ArcaneNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
