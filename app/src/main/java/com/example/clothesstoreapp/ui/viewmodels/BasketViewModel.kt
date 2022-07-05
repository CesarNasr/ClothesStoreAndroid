package com.example.clothesstoreapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstoreapp.R
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.domain.repository.Repository
import com.example.clothesstoreapp.ui.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
/**
 * View model holding the logic data of it's corresponding view (fragment / activity)
 */
@HiltViewModel
class BasketViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val applicationContext: Context,
    ) : ViewModel() {

    private val _basketUiState = MutableStateFlow<UiState>(UiState.Empty)
    val basketUiState: StateFlow<UiState> = _basketUiState

    private val _totalPrice = MutableStateFlow("")
    val totalPrice: StateFlow<String> = _totalPrice

    private val _showEmptyView = MutableStateFlow(false)
    val showEmptyView: StateFlow<Boolean> = _showEmptyView

    fun fetchBasketProducts() {
        viewModelScope.launch {
            try {
                val result = repository.fetchBasketItems()
                calculateTotal(result)
                toggleEmptyView(result.isEmpty())
                _basketUiState.value = UiState.Loaded(data = result)
            } catch (e: Exception) {
                _basketUiState.value = UiState.Error(applicationContext.resources.getString(R.string.something_went_wrong))
            }
        }
    }

    fun deleteBasketProducts(productId: Int, onSuccess: (List<Product>) -> Unit) {
        viewModelScope.launch {
            try {
                val result = repository.deleteBasketItemAndFetch(productId)
                calculateTotal(result)
                toggleEmptyView(result.isEmpty())

                withContext(Dispatchers.Main){
                    onSuccess(result)
                }
            } catch (e: Exception) {
                deleteBasketProducts(productId, onSuccess)
            }
        }
    }


    private fun calculateTotal(items: List<Product>) {
        var total = 0.0
        for (item in items) {
            total += (item.price * (item.qty ?: 1))
        }
        _totalPrice.value = "$$total"
    }

    private fun toggleEmptyView(visible: Boolean) {
        _showEmptyView.value = visible
    }


    /**
    NOTE: for the room db operations, I could have used the following async await,
    if I wanted to wait for the room db completion and check for the results:

    val content = async() {
    repository.deleteWishlistItem(product) --> a suspend fun
    }

    var result = content.await()
     */

}