package com.jagteshwar.flickrclient.data.repository

import com.jagteshwar.flickrclient.data.remote.data_source.FlickrApi
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDetailDto
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDto
import com.jagteshwar.flickrclient.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val flickrApi: FlickrApi
): PhotoRepository {
    override suspend fun searchPhotos(tag: String, page: Int): List<PhotoDto> {
        return flickrApi.searchPhotos(tag, page)
    }

    override suspend fun getPhotoDetail(photoId: String): PhotoDetailDto {
        return flickrApi.getPhotoDetail(photoId)
    }
}