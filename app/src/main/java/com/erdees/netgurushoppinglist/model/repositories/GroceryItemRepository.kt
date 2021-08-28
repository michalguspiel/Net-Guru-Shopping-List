package com.erdees.netgurushoppinglist.model.repositories

import com.erdees.netgurushoppinglist.model.GroceryItem
import com.erdees.netgurushoppinglist.model.dao.GroceryItemDao
import javax.inject.Inject

class GroceryItemRepository @Inject constructor(private val groceryItemDao: GroceryItemDao) {


    fun getGroceriesForShoppingList(id: Long) = groceryItemDao.getGroceriesForShoppingList(id)

    suspend fun insertGroceryItem(groceryItem: GroceryItem) = groceryItemDao.insertGroceryItem(groceryItem)

    suspend fun deleteGroceryItem(groceryItem: GroceryItem) = groceryItemDao.deleteGroceryItem(groceryItem)

    suspend fun editGroceryItem(groceryItem: GroceryItem) = groceryItemDao.editGroceryItem(groceryItem)

}