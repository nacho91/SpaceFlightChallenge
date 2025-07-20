package com.nacho.spaceflight.challenge.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        Scaffold { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = "list"
            ) {
                composable("list") {
                    ListScreen(
                        onArticleClick = { articleId ->
                            navController.navigate("detail/$articleId")
                        }
                    )
                }

                composable(
                    "detail/{articleId}",
                    listOf(navArgument("articleId") { type = NavType.IntType })
                ) {
                    val articleId = it.arguments?.getInt("articleId") ?: return@composable
                    DetailScreen(articleId = articleId, onBack = { navController.navigateUp() })
                }
            }
        }
    }
}