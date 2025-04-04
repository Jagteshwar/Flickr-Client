package com.jagteshwar.flickrclient.presentation.search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val tagState by viewModel.tagState.collectAsState()
    val lazyListState = rememberLazyGridState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == uiState.photos.size - 1 && uiState.photos.isNotEmpty() && !uiState.isLoading) {
                    viewModel.loadNext()
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(MediumPadding)
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
                onClick = { viewModel.searchPhotos(tagState) },
                enabled = !uiState.isLoading
            ) {
                Text(text = "Search")
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.photos.isEmpty() && !uiState.isLoading) {
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
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState
                ) {
                    items(uiState.photos.size) { index ->
                        val photo = uiState.photos[index]
                        AsyncImage(
                            model = photo.url,
                            contentDescription = photo.title,
                            modifier = Modifier
                                .width(MediumWidth)
                                .height(MediumHeight)
                                .padding(SmallPadding)
                                .clip(RoundedCornerShape(RoundedCornerShapeMedium))
                                .clickable { navController.navigate(Screen.DetailScreen.route + "/${photo.id}") },
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

            if (uiState.error != null) {
                Text(
                    text = uiState.error ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(MediumPadding)
                )
            }
        }


    }

}