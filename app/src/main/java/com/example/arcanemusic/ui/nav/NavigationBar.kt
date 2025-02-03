package com.example.arcanemusic.ui.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.arcanemusic.R

sealed class NavigationBarItem(val route: String, val icon: Int, val title: String) {
    data object Home : NavigationBarItem("home", R.drawable.ic_launcher_background, "Home")
    data object Playlist :
        NavigationBarItem("playlist", R.drawable.ic_launcher_background, "Playlist")
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
                        ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.title
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