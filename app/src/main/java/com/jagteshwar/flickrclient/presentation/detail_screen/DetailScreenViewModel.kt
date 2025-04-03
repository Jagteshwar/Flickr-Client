package com.jagteshwar.flickrclient.presentation.detail_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagteshwar.flickrclient.domain.model.PhotoDetail
import com.jagteshwar.flickrclient.domain.usecases.GetPhotoInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getPhotoInfoUseCase: GetPhotoInfoUseCase
): ViewModel(){

    private val _photoDetail = MutableStateFlow<PhotoDetail?>(null)
    val photoDetail: StateFlow<PhotoDetail?> = _photoDetail

    fun getPhotoDetail(photoId: String){
    viewModelScope.launch {
        _photoDetail.value = getPhotoInfoUseCase(photoId)
        Log.d("Flickr App Detail Screen", photoDetail.value.toString())
    }
    }
}