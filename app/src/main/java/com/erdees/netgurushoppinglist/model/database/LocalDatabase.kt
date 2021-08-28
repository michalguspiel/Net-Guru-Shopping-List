package com.erdees.netgurushoppinglist.model.database

import android.content.Context
import androidx.room.*
import com.erdees.netgurushoppinglist.model.GroceryItem
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.dao.GroceryItemDao
import com.erdees.netgurushoppinglist.model.dao.ShoppingListsDao

@Database(entities = [(ShoppingList::class),(GroceryItem::class)], version = 1)
@TypeConverters(Converter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListsDao

    abstract fun groceryItemDao() : GroceryItemDao


}