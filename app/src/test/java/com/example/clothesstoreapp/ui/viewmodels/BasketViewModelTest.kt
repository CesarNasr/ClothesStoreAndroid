package com.example.clothesstoreapp.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.clothesstoreapp.MainCoroutineRule
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.repository.MockRepositoryImpl
import com.example.clothesstoreapp.ui.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class BasketViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: BasketViewModel
    private lateinit var mockRepositoryImpl: MockRepositoryImpl

    @Before
    fun setup() {
        mockRepositoryImpl = MockRepositoryImpl()
        viewModel = BasketViewModel(mockRepositoryImpl, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `fetch product items returns state loaded`() {
        mockRepositoryImpl.resetRepoData()

        viewModel.fetchBasketProducts()
        val response = viewModel.basketUiState.value

        assertThat(response).isInstanceOf(UiState.Loaded::class.java)
    }

    @Test
    fun `fetch product items with internet error returns state error`() {
        mockRepositoryImpl.setShouldReturnNetworkError(true)
        mockRepositoryImpl.resetRepoData()

        viewModel.fetchBasketProducts()
        val response = viewModel.basketUiState.value
        assertThat(response).isInstanceOf(UiState.Loaded::class.java)
    }


    @Test
    fun `delete product item with not existing id operation fails`() {
        mockRepositoryImpl.resetRepoData()

        mainCoroutineRule.launch {
            addRandomItemsToDb(3)
            viewModel.deleteBasketProducts(-1) {
                assertThat(it.size).isEqualTo(3)
            }
        }
    }


    @Test
    fun `delete product item with not existing id returns remaining items success`() {
        mockRepositoryImpl.resetRepoData()

        mainCoroutineRule.launch {
            addRandomItemsToDb(3)
            viewModel.deleteBasketProducts(1) {
                assertThat(it.size).isEqualTo(2)
            }
        }
    }


    private fun generateMockProduct(id: String) =
        Product(id, null, "someImageUrl", "White Shoes", null, 10.0, null, null)

    private suspend fun addRandomItemsToDb(count: Int) {
        for (i in 1 until count) {
            mockRepositoryImpl.insertBasketItem(generateMockProduct(i.toString()))
        }
    }

}