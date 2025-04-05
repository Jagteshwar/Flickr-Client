package com.jagteshwar.flickrclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.jagteshwar.flickrclient.presentation.navigation.AppNavigation
import com.jagteshwar.flickrclient.ui.theme.FlickrClientTheme
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.Logger
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlickrClientTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

