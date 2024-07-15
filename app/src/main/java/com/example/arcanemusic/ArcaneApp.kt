package com.example.arcanemusic

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arcanemusic.navigation.ArcaneNavHost

@Composable
fun ArcaneApp(navController: NavHostController = rememberNavController()) {
    ArcaneNavHost(navController = navController)
}
