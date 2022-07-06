package com.example.clothesstoreapp.data.localdatasource.dao


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.clothesstoreapp.data.localdatasource.database.AppDatabase
import com.example.clothesstoreapp.data.localdatasource.model.Basket
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BasketDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: BasketDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.BasketDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertBasketItem() = runTest {
        val basketItem = Basket(1, "1", "someImageUrl", "White Shoes", 10.0, null)
        dao.insertItem(basketItem)

        val allItems = dao.getAll()

        assertThat(allItems).contains(basketItem)
    }

    @Test
    fun deleteBasketItem() = runTest {
        val basketItem = Basket(1, "1", "someImageUrl", "White Shoes", 10.0, null)
        dao.insertItem(basketItem)
        dao.deleteBasketItem(basketItem)

        val allShoppingItems = dao.getAll()

        assertThat(allShoppingItems).doesNotContain(basketItem)
    }

    @Test
    fun getTotalPriceSum() = runTest {
        val basketItem1 = Basket(1, "1", "someImageUrl", "White Shoes", 10.0, 3)
        val basketItem2 = Basket(2, "2", "someImageUrl", "Red Shoes", 14.0, 1)
        val basketItem3 = Basket(3, "3", "someImageUrl", "Yellow Shoes", 100.0, 2)
        dao.insertItem(basketItem1)
        dao.insertItem(basketItem2)
        dao.insertItem(basketItem3)

        val items = dao.getAll()

        var totalPrice = 0.0

        for (i in items.indices) {
            totalPrice += (items[i].price * items[i].qty!!)
        }

        assertThat(totalPrice.toInt()).isEqualTo(((3 * 10f) + (1 * 14f) + (2 * 100)).toInt())
    }

    @Test
    fun getBasketItemById() = runTest {
        val basketItem = Basket(1, "1", "someImageUrl", "White Shoes", 10.0, null)
        dao.insertItem(basketItem)

        val allItems = dao.getBasketItemById(1)

        for (i in allItems.indices) {
            assertThat(allItems[i].productId.toInt()).isEqualTo(1)
        }
    }




    @Test
    fun getTotalBasketItems() = runTest {
        val basketItem1 = Basket(1, "1", "someImageUrl", "White Shoes", 10.0, 3)
        val basketItem2 = Basket(2, "2", "someImageUrl", "Red Shoes", 14.0, 1)
        val basketItem3 = Basket(3, "3", "someImageUrl", "Yellow Shoes", 100.0, 2)
        dao.insertItem(basketItem1)
        dao.insertItem(basketItem2)
        dao.insertItem(basketItem3)
        val size = dao.getBasketCount().first()
        assertThat(size).isEqualTo(3)
    }
}













