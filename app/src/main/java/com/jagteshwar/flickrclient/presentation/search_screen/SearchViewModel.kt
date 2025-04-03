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
class SearchViewModel @Inject constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    private val _listState = MutableStateFlow<List<Photo>>(emptyList())
    val listState: StateFlow<List<Photo>> = _listState.asStateFlow()
    var currentTag: String = ""
    var currentPage: Int = 1


    fun searchPhotos(tag: String) {
        currentTag = tag
        currentPage = 1
        viewModelScope.launch {
          _listState.value =  searchPhotosUseCase(tag, currentPage)
        }
    }

    fun loadNext(){
        currentPage++
        viewModelScope.launch {
            _listState.value += searchPhotosUseCase(currentTag, currentPage)
        }
    }
}