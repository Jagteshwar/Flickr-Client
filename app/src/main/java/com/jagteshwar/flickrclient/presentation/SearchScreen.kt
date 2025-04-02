package com.jagteshwar.flickrclient.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun SearchScreen(modifier: Modifier, viewModel: SearchViewModel = viewModel()) {
    val photos = viewModel.listState.collectAsState()
    var tagState by remember {
        mutableStateOf("")
    }
    Log.d("Flickr App", photos.value.toString())


    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = tagState,
                onValueChange = {
                    tagState = it
                },
                modifier = Modifier.weight(1f),
                label = { Text(text = "Search Here...") }
            )

            Button(onClick = { viewModel.searchPhotos(tagState) }) {
                Text(text = "Search")
            }
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(photos.value.size) { index ->
                val photo = photos.value[index]
                AsyncImage(
                    model = photo.url,
                    contentDescription =photo.title,
                    modifier = Modifier
                        .width(180.dp)
                        .height(180.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { },
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery)
                )
            }

        }
    }

}
