package com.jagteshwar.flickrclient.presentation.navigation

sealed class Screen(val route: String) {
    object SearchScreen: Screen("search_screen")
    object DetailScreen: Screen("detail_screen")
}