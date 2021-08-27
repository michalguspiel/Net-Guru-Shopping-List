package com.erdees.netgurushoppinglist.model.database

import android.content.Context
import androidx.room.*
import com.erdees.netgurushoppinglist.model.ShoppingList
import com.erdees.netgurushoppinglist.model.dao.ShoppingListDao

@Database(entities = [(ShoppingList::class)], version = 1)
@TypeConverters(Converter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

   // abstract fun groceryItemDao() : ItemCategoryDao


    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}