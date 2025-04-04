package com.jagteshwar.flickrclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jagteshwar.flickrclient.presentation.navigation.AppNavigation
import com.jagteshwar.flickrclient.ui.theme.FlickrClientTheme
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.Logger
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.info("MainActivity launched.")
        enableEdgeToEdge()
        setContent {
            FlickrClientTheme {
              AppNavigation()
            }
        }
    }
}

