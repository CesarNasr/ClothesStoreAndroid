package com.example.clothesstoreapp.di


import com.example.clothesstoreapp.ui.adapters.BasketAdapter
import com.example.clothesstoreapp.ui.adapters.CatalogueAdapter
import com.example.clothesstoreapp.ui.adapters.WishListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dependency injection class that provides instances related to UI utils
 */

@Module
@InstallIn(SingletonComponent::class)
object UiModule {
    @Provides
    fun provideCatalogueAdapter(): CatalogueAdapter {
        return CatalogueAdapter()
    }

    @Provides
    fun provideWishlistAdapter(): WishListAdapter {
        return WishListAdapter()
    }

    @Provides
    fun provideBasketAdapter(): BasketAdapter {
        return BasketAdapter()
    }


}