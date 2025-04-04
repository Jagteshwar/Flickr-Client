package com.jagteshwar.flickrclient.presentation.search_screen

import com.jagteshwar.flickrclient.data.repository.PhotoRepositoryImplTest
import com.jagteshwar.flickrclient.domain.model.Photo
import com.jagteshwar.flickrclient.domain.usecases.SearchPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class SearchViewModelTest{

   private lateinit var searchViewModel: SearchViewModel
   private lateinit var fakeRepositoryImplTest: PhotoRepositoryImplTest
   private lateinit var useCase: SearchPhotosUseCase

   @Before
   fun setup(){
       Dispatchers.setMain(Dispatchers.Unconfined)
       fakeRepositoryImplTest = PhotoRepositoryImplTest()
       useCase = SearchPhotosUseCase(fakeRepositoryImplTest)
       searchViewModel = SearchViewModel(useCase)
   }

    @After
    fun teardown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch list of photos, returns photo list`() = runTest{
        val tag = "moon"
        val expectedPhotos = listOf(
        Photo(id = "1", title = "photo 1", url = "https://www.example.com/photo1.jpg"),
        Photo(id = "2", title = "photo 2", url = "https://www.example.com/photo2.jpg")
        )

        fakeRepositoryImplTest.addPhoto(*expectedPhotos.toTypedArray())

        searchViewModel.searchPhotos(tag)

        val uiState = searchViewModel.uiState.value
        assertEquals("photo list should be same", expectedPhotos.size, uiState.photos.size)
        assertEquals("First Photo id should match", expectedPhotos[0].id, uiState.photos[0].id )
        assertEquals("Photo title should match", expectedPhotos[1].title, uiState.photos[1].title)
        assertFalse("Loading should be false after fetch", uiState.isLoading)
        assertEquals("Error Should be null", null, uiState.error)
    }
}