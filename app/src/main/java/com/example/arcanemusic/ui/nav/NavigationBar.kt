package com.example.arcanemusic.ui.nav

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.arcanemusic.R

sealed class NavigationBarItem(val route: String, val icon: Int, val title: String) {
    data object Home : NavigationBarItem("home", R.drawable.home, "Home")
    data object Playlist :
        NavigationBarItem("playlist", R.drawable.playlist, "Playlist")
}

@Composable
fun NavigationBar(navController: NavController) {
    val items = listOf(
        NavigationBarItem.Home,
        NavigationBarItem.Playlist
    )
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}