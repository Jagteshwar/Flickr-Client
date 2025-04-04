package com.jagteshwar.flickrclient.presentation.detail_screen

import com.jagteshwar.flickrclient.domain.model.PhotoDetail

data class DetailUiState(
    val photoDetail: PhotoDetail? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
