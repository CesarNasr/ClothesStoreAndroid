package com.example.clothesstoreapp.ui.composeuisample.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstoreapp.R
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.data.network.utils.Resource
import com.example.clothesstoreapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CatalogueComposeViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val applicationContext: Context,

    ) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.fetchProducts()
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            _uiState.value = UiState.Loaded(it.products)
                        }
                    }
                    is Resource.Error -> {
                        onErrorOccurred(false)
                    }

                    is Resource.Loading -> {
                    }
                }
            } catch (e: Exception) {
                if (e is HttpException) {
                    onErrorOccurred(true)
                }
            }
        }
    }


    private fun onErrorOccurred(isInternetError: Boolean) {
        var errorMessage = applicationContext.getString(R.string.something_went_wrong)
        if (isInternetError) errorMessage =
            applicationContext.getString(R.string.check_internet_connection)

        _uiState.value = UiState.Error(
            errorMessage
        )
    }

}

sealed class UiState {
    object Empty : UiState()
    object Loading : UiState()
    class Loaded(val data: List<Product>) : UiState()
    class Error(val message: String) : UiState()
}