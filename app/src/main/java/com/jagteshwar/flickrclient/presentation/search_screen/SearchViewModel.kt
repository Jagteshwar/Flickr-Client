package com.jagteshwar.flickrclient.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagteshwar.flickrclient.domain.model.Photo
import com.jagteshwar.flickrclient.domain.usecases.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SearchViewModel @Inject constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _tagState = MutableStateFlow("")
    val tagState: StateFlow<String> = _tagState

    var currentPage: Int = 1

    fun updateTag(tag:String){
        _tagState.value = tag
    }


    fun searchPhotos(tag: String) {
        _tagState.value = tag
        currentPage = 1
        fetchPhotos(false)
    }

    fun loadNext(){
        if(_uiState.value.isLoading) return
        currentPage++
        fetchPhotos(true)
    }

    private fun fetchPhotos(append: Boolean){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try{
                val newPhotos = searchPhotosUseCase(_tagState.value, currentPage)
                _uiState.value = SearchUiState(
                    photos = if(append) _uiState.value.photos + newPhotos else newPhotos,
                    isLoading = false
                )
            }catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load photos: ${e.message}"
                )
            }
        }
    }
}