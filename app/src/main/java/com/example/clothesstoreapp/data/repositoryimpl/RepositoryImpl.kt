package com.example.clothesstoreapp.data.repositoryimpl

import com.example.clothesstoreapp.data.model.ApiEntry
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.data.network.api.ApiService
import com.example.clothesstoreapp.domain.repository.Repository
import com.example.clothesstoreapp.data.localdatasource.database.AppDatabase
import com.example.clothesstoreapp.data.utils.BasketMapper
import com.example.clothesstoreapp.data.utils.WishlistMapper
import com.example.clothesstoreapp.data.network.utils.Resource
import com.example.clothesstoreapp.data.network.utils.ResponseConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  Actual implementation of the repository that communicates with remote source and to local database
 *  Repository acts as a single source of truth for data in our app
 */
class RepositoryImpl
@Inject constructor(
    private val apiService: ApiService,
    private val responseConverter: ResponseConverter,
    private val db: AppDatabase,
    private val wishlistMapper: WishlistMapper,
    private val basketMapper : BasketMapper,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {


    override suspend fun fetchProducts() = withContext(ioDispatcher){
         responseConverter.responseToResult(
            apiService.fetchProducts()
        )
    }

    override suspend fun fetchWishlistItems() = withContext(ioDispatcher){
         wishlistMapper.mapFromEntityList(db.WishlistDao().getAll())
    }

    override suspend fun insertWishlistItem(item: Product)= withContext(ioDispatcher){
         db.WishlistDao().insertItem(wishlistMapper.mapToEntity(item))
    }
    override suspend fun deleteWishlistItem(item: Product) = withContext(ioDispatcher){
         db.WishlistDao().deleteItemAndGetCount(wishlistMapper.mapToEntity(item))
    }

    /**
     * I have used flows and coroutines for applying reactive programming where the wishlist and basket badges
     * are always observed form the ROOM database and thus the UI will always be updated when removing/adding items to
     * the database table.
     */
    override suspend fun getWishListCount() = withContext(ioDispatcher){
        db.WishlistDao().getReactiveWishListCount().transform {
            emit(it)
        }
    }

    override suspend fun getBasketCount()= withContext(ioDispatcher){
        db.BasketDao().getBasketCount().transform {
            emit(it)
        }
    }

    override suspend fun deleteBasketItemAndRefresh(productId : Int) = withContext(ioDispatcher){
        basketMapper.mapFromEntityList(db.BasketDao().deleteBasketEntryAndRefresh(productId))
    }

    override suspend fun fetchBasketItems() = withContext(ioDispatcher){
        basketMapper.mapFromEntityList(db.BasketDao().getAllWithQty())
    }

    override suspend fun insertBasketItem(item: Product)= withContext(ioDispatcher){
        db.BasketDao().insertItem(basketMapper.mapToEntity(item))
    }

}