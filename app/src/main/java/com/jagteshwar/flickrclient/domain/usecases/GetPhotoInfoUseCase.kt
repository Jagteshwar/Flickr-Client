package com.jagteshwar.flickrclient.domain.usecases

import com.jagteshwar.flickrclient.data.mapper.toPhotoDetail
import com.jagteshwar.flickrclient.domain.model.PhotoDetail
import com.jagteshwar.flickrclient.domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotoInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(photoId: String): PhotoDetail {
        return repository.getPhotoDetail(photoId).toPhotoDetail()
    }
}