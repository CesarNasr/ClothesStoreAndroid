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
import javax.inject.Inject
/**
 * View model holding the logic data of it's corresponding view (fragment / activity)
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val applicationContext: Context,

    ) : ViewModel() {

    private val _onItemInserted = MutableStateFlow<UiState>(UiState.Empty)
    val onItemInserted: StateFlow<UiState> = _onItemInserted

    fun addToWishList(product: Product) {
        viewModelScope.launch {
            try {
                repository.insertWishlistItem(product)
                _onItemInserted.value = UiState.Loaded(message = applicationContext.resources.getString(R.string.added_to_wishlist))
            } catch (e: Exception) {
                _onItemInserted.value = UiState.Error(applicationContext.resources.getString(R.string.something_went_wrong))
            }

        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                repository.insertBasketItem(product)
                _onItemInserted.value = UiState.Loaded(message = applicationContext.resources.getString(R.string.item_added_cart))
            } catch (e: Exception) {
                _onItemInserted.value = UiState.Error(applicationContext.resources.getString(R.string.something_went_wrong))
            }
        }
    }

}