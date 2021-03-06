package com.erdees.netgurushoppinglist.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erdees.netgurushoppinglist.model.models.GroceryItem


@Dao
interface GroceryItemDao {

    @Query("SELECT * FROM grocery_item WHERE hostListId = :shoppingListId ORDER BY name ASC")
    fun getGroceriesForShoppingList(shoppingListId : Long) : LiveData<List<GroceryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroceryItem(groceryItem: GroceryItem)

    @Delete
    suspend fun deleteGroceryItem(groceryItem: GroceryItem)

    @Update
    suspend fun editGroceryItem(groceryItem: GroceryItem)

}