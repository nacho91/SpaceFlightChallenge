package com.nacho.spaceflight.challenge.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nacho.spaceflight.challenge.ui.detail.DetailScreen
import com.nacho.spaceflight.challenge.ui.list.ListScreen

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "list"
        ) {
            composable("list") {
                ListScreen()
            }

            composable(
                "detail/{articleId}",
                listOf(navArgument("articleId") { type = NavType.IntType })
            ) {
                val articleId = it.arguments?.getInt("articleId") ?: return@composable
                DetailScreen(articleId = articleId)
            }
        }
    }
}