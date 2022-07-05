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
class WishlistViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val applicationContext: Context,
    ) : ViewModel() {

    private val _addToBasketState = MutableStateFlow<UiState>(UiState.Empty)
    val addToBasketState: StateFlow<UiState> = _addToBasketState

    private val _fetchWishListUiState = MutableStateFlow<UiState>(UiState.Empty)
    val fetchWishListUiState: StateFlow<UiState> = _fetchWishListUiState

    private val _showEmptyView = MutableStateFlow(false)
    val showEmptyView: StateFlow<Boolean> = _showEmptyView


    fun fetchWishListProducts() {
        viewModelScope.launch {
            try {
                val result = repository.fetchWishlistItems()
                toggleEmptyView(result.isEmpty())

                _fetchWishListUiState.value = UiState.Loaded(data = result)
            } catch (e: Exception) {
                _fetchWishListUiState.value = UiState.Error(applicationContext.resources.getString(
                    R.string.something_went_wrong))
            }
        }
    }


    fun addItemToBasket(product: Product) {
        viewModelScope.launch {
            try {
                repository.insertBasketItem(product)
                val itemCount = repository.deleteWishlistItem(product)
                toggleEmptyView(itemCount == 0)

                _addToBasketState.value = UiState.Loaded(product)
            } catch (e: Exception) {
                _addToBasketState.value = UiState.Error()
            }
        }
    }

    fun removeItemFromWishlist(product: Product, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
               val itemCount = repository.deleteWishlistItem(product)
                toggleEmptyView(itemCount == 0)
                withContext(Dispatchers.Main){
                    onSuccess.invoke()
                }

            } catch (e: Exception) {
                _addToBasketState.value = UiState.Error()
            }
        }
    }
    
    /**
    NOTE: for the room db operations, I could have used the following async await,
    if I wanted to wait for the room db completion and check for the results:

    val content = async() {
        repository.deleteWishlistItem(product) --> a suspend fun
    }

    var result = content.await()
     */

    private fun toggleEmptyView(visible: Boolean) {
        _showEmptyView.value = visible
    }
}