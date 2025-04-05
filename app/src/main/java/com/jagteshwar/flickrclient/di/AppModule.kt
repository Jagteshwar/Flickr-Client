package com.jagteshwar.flickrclient.di

import android.app.Application
import com.jagteshwar.flickrclient.data.remote.data_source.FlickrApi
import com.jagteshwar.flickrclient.data.repository.PhotoRepositoryImpl
import com.jagteshwar.flickrclient.domain.repository.PhotoRepository
import com.jagteshwar.flickrclient.domain.usecases.GetPhotoInfoUseCase
import com.jagteshwar.flickrclient.domain.usecases.SearchPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlickrApi(): FlickrApi = FlickrApi()

    @Provides
    @Singleton
    fun providePhotoRepository(flickrApi: FlickrApi): PhotoRepository = PhotoRepositoryImpl(flickrApi)

    @Provides
    @Singleton
    fun provideSearchPhotosUseCase(photoRepository: PhotoRepository): SearchPhotosUseCase = SearchPhotosUseCase(photoRepository)

    @Provides
    @Singleton
    fun provideGetPhotoDetailUseCase(photoRepository: PhotoRepository): GetPhotoInfoUseCase = GetPhotoInfoUseCase(photoRepository)

}