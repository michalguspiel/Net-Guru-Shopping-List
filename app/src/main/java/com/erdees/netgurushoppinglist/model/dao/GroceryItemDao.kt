package com.erdees.netgurushoppinglist.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erdees.netgurushoppinglist.model.GroceryItem


@Dao
interface GroceryItemDao {

    @Query("SELECT * FROM grocery_item WHERE hostListId = :shoppingListId ORDER BY name ASC")
    fun getGroceriesForShoppingList(shoppingListId : Long) : LiveData<List<GroceryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroceryItem(groceryItem: GroceryItem)

    @Delete
    fun deleteGroceryItem(groceryItem: GroceryItem)

}