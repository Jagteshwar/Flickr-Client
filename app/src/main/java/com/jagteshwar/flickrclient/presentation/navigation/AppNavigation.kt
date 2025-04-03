package com.jagteshwar.flickrclient.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jagteshwar.flickrclient.presentation.detail_screen.DetailScreen
import com.jagteshwar.flickrclient.presentation.search_screen.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
        composable(Screen.SearchScreen.route){
            SearchScreen(navController)
        }

        composable(Screen.DetailScreen.route + "/{photoId}"){backStackEntry->
            val photoId = backStackEntry.arguments?.getString("photoId")
            if (photoId != null) {
                DetailScreen(navController, photoId)
            }
        }
    }
}