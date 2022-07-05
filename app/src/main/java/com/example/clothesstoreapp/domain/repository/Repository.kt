package com.example.clothesstoreapp.domain.repository

import com.example.clothesstoreapp.data.model.ApiEntry
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.data.network.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for the purpose of abstraction between data layer and another presentation layer
 */

interface Repository {

    /**
     * Fetch data from remote datasource
     */
    suspend fun fetchProducts(): Resource<ApiEntry>

    /**
     * Fetch and Insert data to local datasource of wishlist table
     */
    suspend fun fetchWishlistItems(): List<Product>
    suspend fun insertWishlistItem(item : Product): Long
    suspend fun deleteWishlistItem(item : Product): Int
    suspend fun getWishListCount() : Flow<Int>


    /**
     * Fetch and Insert data to local datasource of basket table
     */
    suspend fun fetchBasketItems(): List<Product>
    suspend fun insertBasketItem(item : Product): Long
    suspend fun getBasketCount() : Flow<Int>
    suspend fun deleteBasketItemAndFetch(productId : Int) : List<Product>

}