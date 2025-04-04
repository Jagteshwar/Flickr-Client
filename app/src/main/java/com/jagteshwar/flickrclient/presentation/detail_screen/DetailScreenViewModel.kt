package com.jagteshwar.flickrclient.presentation.detail_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagteshwar.flickrclient.domain.model.PhotoDetail
import com.jagteshwar.flickrclient.domain.usecases.GetPhotoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getPhotoInfoUseCase: GetPhotoInfoUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getPhotoDetail(photoId: String){
    viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        try {
            val photoDetail = getPhotoInfoUseCase(photoId)
            _uiState.value = _uiState.value.copy(
                photoDetail = photoDetail,
                isLoading = false
            )
        }catch (e: Exception){
            _uiState.value = _uiState.value.copy(
                photoDetail = null,
                isLoading = false,
                error = "Failed to load photo detail: ${e.message}"
            )
        }
    }
    }
}