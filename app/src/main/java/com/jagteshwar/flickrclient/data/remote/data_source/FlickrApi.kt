package com.jagteshwar.flickrclient.data.remote.data_source

import com.googlecode.flickrjandroid.Flickr
import com.googlecode.flickrjandroid.REST
import com.googlecode.flickrjandroid.Transport
import com.googlecode.flickrjandroid.photos.PhotosInterface
import com.googlecode.flickrjandroid.photos.SearchParameters
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDetailDto
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDto
import com.jagteshwar.flickrclient.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FlickrApi {

    private val photosInterface: PhotosInterface
init {
    val flickr = Flickr(Constants.API_KEY, REST())
    photosInterface = flickr.photosInterface
}

    suspend fun searchPhotos(tags: String, page: Int): List<PhotoDto> = withContext(Dispatchers.IO) {
        val params = SearchParameters()
        params.tags = arrayOf(tags)

        val photos = photosInterface.search(params, 12, page)
        photos.map { PhotoDto(it.id, it.title, it.mediumUrl.toString()) }
    }
}