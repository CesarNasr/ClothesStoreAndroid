package com.example.clothesstoreapp.ui.utils

import com.example.clothesstoreapp.data.model.Product

sealed class UiState {
    object Empty : UiState()
    object Loading : UiState()
    class Loaded(val itemData : Product ?= null, val data: List<Product>? = null, val message: String? = null) : UiState()
    class Error(val message: String? = null) : UiState()
}