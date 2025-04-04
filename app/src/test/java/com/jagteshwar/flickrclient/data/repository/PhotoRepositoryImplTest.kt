package com.jagteshwar.flickrclient.data.repository


import androidx.compose.runtime.mutableStateOf
import com.jagteshwar.flickrclient.data.mapper.toPhotoDetailDto
import com.jagteshwar.flickrclient.data.mapper.toPhotoDto
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDetailDto
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDto
import com.jagteshwar.flickrclient.domain.model.Photo
import com.jagteshwar.flickrclient.domain.model.PhotoDetail
import com.jagteshwar.flickrclient.domain.repository.PhotoRepository
import org.junit.Assert.*

class PhotoRepositoryImplTest : PhotoRepository {

    private val photos = mutableListOf<Photo>()
    private val photoDetail = mutableMapOf<String, PhotoDetail>()

    fun addPhotoDetail(photoId: String, detail: PhotoDetail) {
        photoDetail[photoId] = detail
    }

    fun addPhoto(vararg photoToAdd: Photo){
        photos.addAll(photoToAdd)
    }

    override suspend fun searchPhotos(tag: String, page: Int): List<PhotoDto> {
        return photos.map { it.toPhotoDto() }
    }

    override suspend fun getPhotoDetail(photoId: String): PhotoDetailDto {
        return photoDetail[photoId]?.toPhotoDetailDto()
            ?: throw Exception("Unexpected Error Occurred.")
    }
}