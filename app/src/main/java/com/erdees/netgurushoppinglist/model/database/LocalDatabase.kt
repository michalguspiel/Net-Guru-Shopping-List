package com.erdees.netgurushoppinglist.model.database

import androidx.room.*
import com.erdees.netgurushoppinglist.model.models.GroceryItem
import com.erdees.netgurushoppinglist.model.models.ShoppingList
import com.erdees.netgurushoppinglist.model.dao.GroceryItemDao
import com.erdees.netgurushoppinglist.model.dao.ShoppingListsDao

@Database(entities = [(ShoppingList::class),(GroceryItem::class)], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListsDao

    abstract fun groceryItemDao() : GroceryItemDao


}