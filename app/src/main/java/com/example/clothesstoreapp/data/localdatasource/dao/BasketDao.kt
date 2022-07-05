package com.example.clothesstoreapp.data.localdatasource.dao

import androidx.room.*
import com.example.clothesstoreapp.data.localdatasource.model.Basket
import kotlinx.coroutines.flow.Flow


@Dao
interface BasketDao {

    @Query("SELECT * FROM basket")
    fun getAll(): List<Basket>

    @Query("SELECT *, COUNT(*) as qty FROM basket GROUP BY productId")
    fun getAllWithQty(): List<Basket>

    @Query("SELECT * FROM basket WHERE productId = :id")
    fun getBasketItemById(id: Int): List<Basket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(basket: Basket): Long

    /**
     * I have used flows and coroutines for applying reactive programming where the wishlist and basket badges
     * are always observed form the ROOM database and thus the UI will always be updated when removing/adding items to
     * the database table.
     */
    @Query("SELECT COUNT(*) FROM basket")
    fun getBasketCount(): Flow<Int>

    @Delete
    fun deleteBasketItem(basketItem: Basket): Int


    @Transaction
    fun deleteBasketEntryAndRefresh(id: Int): List<Basket> {
        val items = getBasketItemById(id) as MutableList
        if (items.isNotEmpty()) {
            deleteBasketItem(items[0])
        }

        return getAllWithQty()
    }
}