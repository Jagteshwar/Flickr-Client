package com.jagteshwar.flickrclient.presentation.search_screen

import com.jagteshwar.flickrclient.domain.model.Photo

data class SearchUiState(
    val photos: List<Photo> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
