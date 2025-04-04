package com.jagteshwar.flickrclient.presentation.search_screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jagteshwar.flickrclient.MainActivity
import com.jagteshwar.flickrclient.data.repository.PhotoRepositoryImpl
import com.jagteshwar.flickrclient.di.AppModule
import com.jagteshwar.flickrclient.domain.usecases.SearchPhotosUseCase
import com.jagteshwar.flickrclient.presentation.navigation.AppNavigation
import com.jagteshwar.flickrclient.ui.theme.FlickrClientTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.MutableStateFlow

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@UninstallModules(AppModule::class)
class SearchScreenTest {

    @Mock
    private lateinit var searchViewModel: SearchViewModel

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        hiltRule.inject()
    }

    @Test
    fun testInitialSearchScreenDisplayCorrectly() {
        composeRule.onNodeWithText("Search a tag.").assertIsDisplayed()
        composeRule.onNodeWithText("Search Here...").assertIsDisplayed()
        composeRule.onNodeWithText("Search").assertIsDisplayed()

    }


}