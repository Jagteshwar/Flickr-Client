package com.jagteshwar.flickrclient.presentation.search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jagteshwar.flickrclient.presentation.navigation.Screen
import com.jagteshwar.flickrclient.utils.Constants.MediumHeight
import com.jagteshwar.flickrclient.utils.Constants.MediumPadding
import com.jagteshwar.flickrclient.utils.Constants.MediumWidth
import com.jagteshwar.flickrclient.utils.Constants.RoundedCornerShapeMedium
import com.jagteshwar.flickrclient.utils.Constants.SmallPadding
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val tagState by viewModel.tagState.collectAsState()
    val lazyListState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember{ SnackbarHostState()}

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val columns = if(isLandscape) GridCells.Fixed(2) else GridCells.Fixed(3)

    LaunchedEffect(key1 = uiState.error) {
        uiState.error?.let {e->
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = e,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == uiState.photos.size - 1 && uiState.photos.isNotEmpty() && !uiState.isLoading) {
                    viewModel.loadNext()
                }
            }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(if (isLandscape) MediumPadding * 2 else MediumPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SmallPadding)

            ) {
                TextField(
                    value = tagState,
                    onValueChange = {
                        viewModel.updateTag(it)
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text(text = "Search Here...") },
                    enabled = !uiState.isLoading
                )

                Button(
                    onClick = {
                        if(tagState.isNotEmpty()){
                            viewModel.searchPhotos(tagState)
                        }else{
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    message = "Search a tag.",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
                    enabled = !uiState.isLoading
                ) {
                    Text(text = "Search")
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (tagState.isEmpty() || (uiState.photos.isEmpty() && !uiState.isLoading)) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Search a tag.")
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_info_details),
                            contentDescription = null
                        )
                    }
                } else if(uiState.photos.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = columns,
                        modifier = Modifier.fillMaxSize(),
                        state = lazyListState,
                        contentPadding = PaddingValues(SmallPadding)
                    ) {
                        items(uiState.photos.size) { index ->
                            val photo = uiState.photos[index]
                            AsyncImage(
                                model = photo.url,
                                contentDescription = photo.title,
                                modifier = Modifier
                                    .width(if (isLandscape) MediumWidth * 0.8f else MediumWidth)
                                    .height(if (isLandscape) MediumHeight * 0.8f else MediumHeight)
                                    .padding(SmallPadding)
                                    .clip(RoundedCornerShape(RoundedCornerShapeMedium))
                                    .clickable {
                                        navController.navigate(Screen.DetailScreen.route + "/${photo.id}")
                                    },
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery)
                            )
                        }

                    }
                }
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}