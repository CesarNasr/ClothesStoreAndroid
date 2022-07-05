package com.example.clothesstoreapp.data.localdatasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.clothesstoreapp.data.localdatasource.model.Basket
import com.example.clothesstoreapp.data.localdatasource.dao.BasketDao
import com.example.clothesstoreapp.data.localdatasource.model.Wishlist
import com.example.clothesstoreapp.data.localdatasource.dao.WishlistDao

@Database(
    entities = [Basket::class, Wishlist::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun BasketDao(): BasketDao
    abstract fun WishlistDao(): WishlistDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "store.db")
            .build()
    }
}