package com.jagteshwar.flickrclient.presentation.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jagteshwar.flickrclient.utils.Constants.CardHeight
import com.jagteshwar.flickrclient.utils.Constants.ElevationMedium
import com.jagteshwar.flickrclient.utils.Constants.ElevationSmall
import com.jagteshwar.flickrclient.utils.Constants.MediumPadding
import com.jagteshwar.flickrclient.utils.Constants.RoundedCornerShapeLarge
import com.jagteshwar.flickrclient.utils.Constants.RoundedCornerShapeMedium
import com.jagteshwar.flickrclient.utils.Constants.SmallPadding
import com.jagteshwar.flickrclient.utils.Constants.SmallWidth

@Composable
fun DetailScreen(
    navController: NavController,
    photoId: String,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val configuration = LocalConfiguration.current
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(key1 = photoId) {
        viewModel.getPhotoDetail(photoId)
    }

    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }

        uiState.error != null -> {
            Text(text = uiState.error ?: "Unexpected Error")
        }

        uiState.photoDetail != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(MediumPadding)
            ) {
                if (isLandscape) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(MediumPadding)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .shadow(
                                    ElevationMedium,
                                    RoundedCornerShape(RoundedCornerShapeLarge)
                                ),
                            shape = RoundedCornerShape(RoundedCornerShapeLarge),
                            colors = CardDefaults.cardColors(contentColor = Color.White)
                        ) {
                            AsyncImage(
                                model = uiState.photoDetail?.url,
                                contentDescription = uiState.photoDetail?.title,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(RoundedCornerShapeLarge)),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(MediumPadding)
                        ) {
                            Text(
                                text = uiState.photoDetail?.title ?: "N/A",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = SmallPadding)
                            )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .shadow(
                                        ElevationSmall, RoundedCornerShape(
                                            RoundedCornerShapeMedium
                                        )
                                    ),
                                shape = RoundedCornerShape(RoundedCornerShapeMedium),
                                colors = CardDefaults.cardColors(contentColor = Color.White)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .padding(MediumPadding)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.spacedBy(SmallPadding)
                                ) {
                                    DetailRow(
                                        label = "Description",
                                        value = uiState.photoDetail?.description ?: "N/A"
                                    )
                                    DetailRow(
                                        label = "Date Taken",
                                        value = uiState.photoDetail?.dateTaken ?: "N/A"
                                    )
                                    DetailRow(
                                        label = "Date Posted",
                                        value = uiState.photoDetail?.datePosted ?: "N/A"
                                    )

                                }
                            }
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { navController.popBackStack() }) {
                                Text(text = "Back")
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(MediumPadding)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CardHeight)
                                .shadow(
                                    ElevationMedium,
                                    RoundedCornerShape(RoundedCornerShapeLarge)
                                ),
                            shape = RoundedCornerShape(RoundedCornerShapeLarge),
                            colors = CardDefaults.cardColors(contentColor = Color.White)
                        ) {
                            AsyncImage(
                                model = uiState.photoDetail?.url,
                                contentDescription = uiState.photoDetail?.title,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(RoundedCornerShapeLarge)),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery)
                            )
                        }
                        Text(
                            text = uiState.photoDetail?.title ?: "N/A",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = SmallPadding)
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .shadow(
                                    ElevationSmall,
                                    RoundedCornerShape(RoundedCornerShapeMedium)
                                ),
                            shape = RoundedCornerShape(RoundedCornerShapeMedium),
                            colors = CardDefaults.cardColors(contentColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(MediumPadding)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.spacedBy(SmallPadding)
                            ) {
                                DetailRow(
                                    label = "Description",
                                    value = uiState.photoDetail?.description ?: "N/A"
                                )
                                DetailRow(
                                    label = "Date Taken",
                                    value = uiState.photoDetail?.dateTaken ?: "N/A"
                                )
                                DetailRow(
                                    label = "Date Posted",
                                    value = uiState.photoDetail?.datePosted ?: "N/A"
                                )
                            }
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { navController.popBackStack() }
                        ) {
                            Text(text = "Back")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SmallPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(SmallWidth)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}