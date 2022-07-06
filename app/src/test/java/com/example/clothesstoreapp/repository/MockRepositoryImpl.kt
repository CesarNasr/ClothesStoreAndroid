package com.example.clothesstoreapp.repository

import com.example.clothesstoreapp.data.model.ApiEntry
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.data.network.utils.Resource
import com.example.clothesstoreapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockRepositoryImpl : Repository {
    private var productItems = mutableListOf<Product>()
    private var wishListItems = mutableListOf<Product>()
    private var basketItems = mutableListOf<Product>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }


    fun resetRepoData() {
        productItems = mutableListOf()
        wishListItems= mutableListOf()
        basketItems= mutableListOf()
    }

    override suspend fun fetchProducts(): Resource<ApiEntry> {
        return if (shouldReturnNetworkError) {
            productItems.addAll(generateMockProductsRemoteData())
            Resource.Success(ApiEntry(productItems))
        } else {
            Resource.Error("")
        }
    }

    override suspend fun fetchWishlistItems(): List<Product> {
        return wishListItems
    }


    override suspend fun insertWishlistItem(item: Product): Long {
        wishListItems.add(item)
        return 1
    }

    override suspend fun deleteWishlistItem(item: Product): Int {
        wishListItems.remove(item)
        return 1
    }

    override suspend fun getWishListCount(): Flow<Int> {
        return flowOf(wishListItems.size)
    }

    override suspend fun fetchBasketItems(): List<Product> {
        return basketItems
    }

    override suspend fun insertBasketItem(item: Product): Long {
        basketItems.add(item)
        return 1
    }

    override suspend fun getBasketCount(): Flow<Int> {
        return flowOf(basketItems.size)
    }

    override suspend fun deleteBasketItemAndRefresh(productId: Int): List<Product> {
        val items = productItems.filter { it.productId == productId.toString() }.toMutableList()

        if (items.isNotEmpty()) {
            items.removeFirst()
        }
        val map = items.groupBy { it.productId }
        val result = mutableListOf<Product>()

        map.forEach { (_, value) ->
            val currentProduct = value[0]
            currentProduct.qty = value.size
            result.add(currentProduct)
        }


        return result
    }


    private fun generateMockProductsRemoteData(): List<Product> {
        return listOf(
            Product("0", null, "someImageUrl", "White Shoes", null, 10.0, null, null),
            Product("1", null, "someImageUrl", "White Shoes", null, 10.0, null, null),
            Product("2", null, "someImageUrl", "White Shoes", null, 10.0, null, null)
        )

    }
}