package com.example.clothesstoreapp.data.localdatasource.dao

import androidx.room.*
import com.example.clothesstoreapp.data.localdatasource.model.Wishlist
import kotlinx.coroutines.flow.Flow


@Dao
interface WishlistDao {

    @Query("SELECT * FROM wishlist")
    fun getAll(): List<Wishlist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(wishlist : Wishlist) : Long

    @Delete
    fun deleteItem(wishlist : Wishlist)

    /**
     * I have used flows and coroutines for applying reactive programming where the wishlist and basket badges
     * are always observed form the ROOM database and thus the UI will always be updated when removing/adding items to
     * the database table.
     */

    @Query("SELECT COUNT(*) FROM wishlist")
    fun getReactiveWishListCount() : Flow<Int>

    @Query("SELECT COUNT(*) FROM wishlist")
    fun getWishListCount() : Int


    @Transaction
    fun deleteItemAndGetCount(wishlist : Wishlist): Int {
        deleteItem(wishlist)
       return getWishListCount()
    }
}