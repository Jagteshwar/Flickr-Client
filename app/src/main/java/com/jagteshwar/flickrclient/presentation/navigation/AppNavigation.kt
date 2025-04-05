package com.jagteshwar.flickrclient.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jagteshwar.flickrclient.presentation.detail_screen.DetailScreen
import com.jagteshwar.flickrclient.presentation.search_screen.SearchScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screen.SearchScreen.route) {
        composable(Screen.SearchScreen.route){
            SearchScreen(navHostController)
        }

        composable(Screen.DetailScreen.route + "/{photoId}"){backStackEntry->
            val photoId = backStackEntry.arguments?.getString("photoId")
            if (photoId != null) {
                DetailScreen(navHostController, photoId)
            }
        }
    }
}