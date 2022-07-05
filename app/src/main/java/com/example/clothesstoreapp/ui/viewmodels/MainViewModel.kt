package com.example.clothesstoreapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothesstoreapp.domain.repository.Repository
import com.example.clothesstoreapp.ui.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * View model holding the logic data of it's corresponding view (fragment / activity)
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _wishListCount = MutableStateFlow(0)
    val wishListCount: StateFlow<Int> = _wishListCount



    private val _basketCount = MutableStateFlow(0)
    val basketCount: StateFlow<Int> = _basketCount

    init {
        getWishListCount()
        getBasketCount()
    }

    private fun getWishListCount() {
        viewModelScope.launch {
            repository.getWishListCount().collect {
                _wishListCount.value = it
            }
        }
    }

    private fun getBasketCount() {
        viewModelScope.launch {
            repository.getBasketCount().collect {
                _basketCount.value = it
            }
        }
    }
}