package com.jagteshwar.flickrclient.presentation.detail_screen

import com.jagteshwar.flickrclient.data.repository.PhotoRepositoryImplTest
import com.jagteshwar.flickrclient.domain.model.PhotoDetail
import com.jagteshwar.flickrclient.domain.usecases.GetPhotoInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DetailScreenViewModelTest{

    private lateinit var detailScreenViewModel: DetailScreenViewModel
    private lateinit var fakeRepositoryImplTest: PhotoRepositoryImplTest
    private lateinit var useCase: GetPhotoInfoUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        fakeRepositoryImplTest = PhotoRepositoryImplTest()
        useCase = GetPhotoInfoUseCase(fakeRepositoryImplTest)
        detailScreenViewModel = DetailScreenViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun teardown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch photo detail, returns photo detail`()  = runTest{
       val photoId = "123"
       val expectedDetail = PhotoDetail(
           id = photoId,
           title = "Test Photo",
           description = "Test Description",
           dateTaken = "2025-04-03",
           datePosted = "2025-04-03",
           url = "https://www.example.com/photo.jpg"
       )
        fakeRepositoryImplTest.addPhotoDetail(photoId, expectedDetail)

        detailScreenViewModel.getPhotoDetail(photoId)

        val uiState = detailScreenViewModel.uiState.value
        assertEquals(expectedDetail.id, uiState.photoDetail?.id)
        assertEquals(expectedDetail.title, uiState.photoDetail?.title)
        assertEquals(expectedDetail.description, uiState.photoDetail?.description)
        assertEquals(expectedDetail.dateTaken, uiState.photoDetail?.dateTaken)
        assertEquals(expectedDetail.datePosted, uiState.photoDetail?.datePosted)
        assertEquals(expectedDetail.url, uiState.photoDetail?.url)

    }
}