package com.example.clothesstoreapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstoreapp.R
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.data.network.utils.Resource
import com.example.clothesstoreapp.domain.repository.Repository
import com.example.clothesstoreapp.ui.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * View model holding the logic data of it's corresponding view (fragment / activity)
 */
@HiltViewModel
class CatalogueViewModel @Inject constructor(
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
                when (val response = repository.fetchProducts()) {
                    is Resource.Success -> {
                        response.data?.let {
                            _uiState.value  = UiState.Loaded(data = it.products as MutableList<Product>)
                        }
                    }
                    is Resource.Error -> {
                        onErrorOccurred(false)
                    }

                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                }
            } catch (e: Exception) {
                if (e is HttpException || e is IOException) {
                    onErrorOccurred(true)
                }
            }
        }
    }


    private fun onErrorOccurred(isInternetError: Boolean) {
        var errorMessage = applicationContext.getString(R.string.something_went_wrong)
        if (isInternetError) errorMessage =
            applicationContext.getString(R.string.check_internet_connection)

        _uiState.value = UiState.Error(message = errorMessage)
    }

    fun onRefreshClicked(){
        fetchProducts()
    }

}