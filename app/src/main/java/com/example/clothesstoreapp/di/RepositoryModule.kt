package com.example.clothesstoreapp.di


import com.example.clothesstoreapp.data.localdatasource.database.AppDatabase
import com.example.clothesstoreapp.data.network.api.ApiService
import com.example.clothesstoreapp.data.network.utils.ResponseConverter
import com.example.clothesstoreapp.domain.repository.Repository
import com.example.clothesstoreapp.data.repositoryimpl.RepositoryImpl
import com.example.clothesstoreapp.data.utils.BasketMapper
import com.example.clothesstoreapp.data.utils.WishlistMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Dependency injection class that provides instances related to repositories
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideResponseConverter(): ResponseConverter {
        return ResponseConverter()
    }

    @Provides
    @Singleton
    fun provideWishListMapper() = WishlistMapper()

    @Provides
    @Singleton
    fun provideBasketMapper() = BasketMapper()


    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        responseConverter: ResponseConverter,
        appDatabase: AppDatabase,
        wishlistMapper: WishlistMapper,
        basketMapper: BasketMapper,
        dispatcher: CoroutineDispatcher
    ): Repository {
        return RepositoryImpl(apiService, responseConverter, appDatabase, wishlistMapper, basketMapper, dispatcher)
    }
}