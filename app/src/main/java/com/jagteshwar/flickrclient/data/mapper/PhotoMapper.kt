package com.jagteshwar.flickrclient.data.mapper

import com.jagteshwar.flickrclient.data.remote.dto.PhotoDetailDto
import com.jagteshwar.flickrclient.data.remote.dto.PhotoDto
import com.jagteshwar.flickrclient.domain.model.Photo
import com.jagteshwar.flickrclient.domain.model.PhotoDetail

fun PhotoDto.toPhoto(): Photo = Photo(
    id = id,
    title = title,
    url = url
)

fun Photo.toPhotoDto(): PhotoDto = PhotoDto(
    id = id,
    title = title,
    url = url
)

fun PhotoDetailDto.toPhotoDetail(): PhotoDetail = PhotoDetail(
    id = id,
    title = title,
     description = description,
    dateTaken = dateTaken,
    datePosted = datePosted,
    url = url
)

fun PhotoDetail.toPhotoDetailDto(): PhotoDetailDto = PhotoDetailDto(
    id = id,
    title = title,
    description = description,
    dateTaken = dateTaken,
    datePosted = datePosted,
    url = url
)