package com.example.clothesstoreapp.data.network.api

import com.example.clothesstoreapp.BuildConfig
import com.example.clothesstoreapp.data.model.ApiEntry
import retrofit2.Response
import retrofit2.http.GET

/**
 * Api Service class calling @GET , @POST,  @DELETE, @UPDATE, @EDIT etc ...
 */
interface ApiService {
    @GET(BuildConfig.API_KEY)
    suspend fun fetchProducts(): Response<ApiEntry>
}