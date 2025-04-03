package com.jagteshwar.flickrclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagteshwar.flickrclient.presentation.detail_screen.DetailScreen
import com.jagteshwar.flickrclient.presentation.detail_screen.DetailScreenViewModel
import com.jagteshwar.flickrclient.presentation.navigation.AppNavigation
import com.jagteshwar.flickrclient.presentation.search_screen.SearchScreen
import com.jagteshwar.flickrclient.presentation.search_screen.SearchViewModel
import com.jagteshwar.flickrclient.ui.theme.FlickrClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlickrClientTheme {
              AppNavigation()
            }
        }
    }
}

