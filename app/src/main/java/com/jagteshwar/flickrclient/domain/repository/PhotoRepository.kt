package com.jagteshwar.flickrclient.domain.repository

import com.jagteshwar.flickrclient.data.remote.dto.PhotoDetailDto
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDto

interface PhotoRepository {
    suspend fun searchPhotos(tag: String, page: Int): List<PhotoDto>
    suspend fun getPhotoDetail(photoId: String): PhotoDetailDto
}