package com.erdees.netgurushoppinglist.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erdees.netgurushoppinglist.model.models.ShoppingList

@Dao
interface ShoppingListsDao {

    @Query("SELECT * FROM SHOPPING_LIST WHERE isActive = 1 ORDER BY creationDate DESC")
    fun getActiveShoppingLists() : LiveData<List<ShoppingList>>

    @Query("SELECT * FROM SHOPPING_LIST WHERE isActive = 0 ORDER BY creationDate DESC")
    fun getArchivedShoppingLists() : LiveData<List<ShoppingList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingList(shoppingList: ShoppingList)

    @Delete
    suspend fun deleteShoppingList(shoppingList: ShoppingList)

    @Update
    suspend fun updateShoppingList(shoppingList: ShoppingList)

}