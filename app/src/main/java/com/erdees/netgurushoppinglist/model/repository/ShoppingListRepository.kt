package com.erdees.netgurushoppinglist.model.repository

import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.dao.ShoppingListDao

class ShoppingListRepository(private val shoppingListDao: ShoppingListDao) {

    val getActiveShoppingLists = shoppingListDao.getActiveShoppingLists()

    val getArchivedShoppingLists = shoppingListDao.getArchivedShoppingLists()

    suspend fun addShoppingList(shoppingList: ShoppingList) =
        shoppingListDao.addShoppingList(shoppingList)

}