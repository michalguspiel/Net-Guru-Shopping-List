package com.erdees.netgurushoppinglist.model.repositories

import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.dao.ShoppingListsDao
import javax.inject.Inject

class ShoppingListRepository
    @Inject constructor(private val shoppingListDao: ShoppingListsDao) {

    val getActiveShoppingLists = shoppingListDao.getActiveShoppingLists()

    val getArchivedShoppingLists = shoppingListDao.getArchivedShoppingLists()

    suspend fun addShoppingList(shoppingList: ShoppingList) =
        shoppingListDao.addShoppingList(shoppingList)

    suspend fun deleteShoppingList(shoppingList: ShoppingList) =
        shoppingListDao.deleteShoppingList(shoppingList)

    suspend fun updateShoppingList(shoppingList: ShoppingList) =
        shoppingListDao.updateShoppingList(shoppingList)
}