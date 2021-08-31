package com.erdees.netgurushoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.erdees.netgurushoppinglist.model.dao.GroceryItemDao
import com.erdees.netgurushoppinglist.model.database.LocalDatabase
import com.erdees.netgurushoppinglist.model.models.GroceryItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@SmallTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GroceriesDaoTest {

    lateinit var dao : GroceryItemDao
    lateinit var database : LocalDatabase


    private val testHostId = 0L

    private val firstTestGroceryItem =
        GroceryItem(1, "First test grocery item", "", testHostId, true)
    private val secondTestGroceryItem =
        GroceryItem(2, "Second test grocery item", "", testHostId, false)


    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.groceryItemDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun givenFirstGroceryItemWasAddedToDatabase_getGroceriesToPresentShouldReturnFirstGroceryItem() =
        runBlockingTest {
            dao.insertGroceryItem(firstTestGroceryItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).contains(firstTestGroceryItem)
        }

    @Test
    fun givenOnlySecondGroceryItemWasAddedToDatabase_getGroceriesToPresentShouldContainSecondGroceryItemAndNotFirst() =
        runBlockingTest {
            dao.insertGroceryItem(secondTestGroceryItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).contains(secondTestGroceryItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).doesNotContain(firstTestGroceryItem)
        }

    @Test
    fun givenBothItemsWereAddedToDatabaseAndThenDeleted_GetGroceriesForShoppingListShouldNotContainAnyOfItems(){
        runBlockingTest {
            dao.insertGroceryItem(firstTestGroceryItem)
            dao.insertGroceryItem(secondTestGroceryItem)
            dao.deleteGroceryItem(firstTestGroceryItem)
            dao.deleteGroceryItem(secondTestGroceryItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).doesNotContain(secondTestGroceryItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).doesNotContain(firstTestGroceryItem)
        }
    }

    @Test
    fun givenFirstItemWasAddedAndThenItQuantityChanged_GetGroceriesForShoppingListShouldContainOnlyUpdatedItem(){
        runBlockingTest {
            dao.insertGroceryItem(firstTestGroceryItem)
            val updatedItem = GroceryItem(firstTestGroceryItem.id,firstTestGroceryItem.name,"New updated quantity",firstTestGroceryItem.hostListId,firstTestGroceryItem.isInBasket)
            dao.editGroceryItem(updatedItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).doesNotContain(firstTestGroceryItem)
            assertThat(dao.getGroceriesForShoppingList(testHostId).getOrAwaitValue()).contains(updatedItem)
        }
    }

}