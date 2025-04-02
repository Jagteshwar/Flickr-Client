package com.jagteshwar.flickrclient.domain.usecases


import com.jagteshwar.flickrclient.data.mapper.toPhoto
import com.jagteshwar.flickrclient.domain.model.Photo
import com.jagteshwar.flickrclient.domain.repository.PhotoRepository
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(tag: String, page: Int): List<Photo>{
        return repository.searchPhotos(tag, page).map { it.toPhoto() }
    }
}