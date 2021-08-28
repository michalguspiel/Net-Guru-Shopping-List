package com.erdees.netgurushoppinglist.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.database.Converter

@Dao
interface ShoppingListsDao {

    @Query("SELECT * FROM SHOPPING_LIST WHERE isActive = 1 ORDER BY timeStamp ASC")
    fun getActiveShoppingLists() : LiveData<List<ShoppingList>>

    @Query("SELECT * FROM SHOPPING_LIST WHERE isActive = 0 ORDER BY timeStamp ASC")
    fun getArchivedShoppingLists() : LiveData<List<ShoppingList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingList(shoppingList: ShoppingList)
}