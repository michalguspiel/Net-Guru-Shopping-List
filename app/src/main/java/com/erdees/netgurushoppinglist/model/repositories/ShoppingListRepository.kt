package com.erdees.netgurushoppinglist.model.repositories

import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.dao.ShoppingListsDao
import javax.inject.Inject

class ShoppingListRepository
    @Inject constructor(private val shoppingListDao: ShoppingListsDao) {

    val getActiveShoppingLists = shoppingListDao.getActiveShoppingLists()

    val getArchivedShoppingLists = shoppingListDao.getArchivedShoppingLists()

    suspend fun addShoppingList(shoppingList: ShoppingList) =
        shoppingListDao.addShoppingList(shoppingList)



}