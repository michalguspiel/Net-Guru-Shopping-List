package com.erdees.netgurushoppinglist.di

import android.app.Activity
import android.content.Context
import androidx.room.Room
import com.erdees.netgurushoppinglist.model.dao.ShoppingListsDao
import com.erdees.netgurushoppinglist.model.database.LocalDatabase
import com.erdees.netgurushoppinglist.model.repositories.BusinessLogicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication  {
        return app as BaseApplication
    }


    @Singleton
    @Provides
    fun provideActivity(@ActivityContext app : Activity) :Activity {
        return app
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app : Context ) = Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "your_db_name"
        ).build()


    @Singleton
    @Provides
    fun provideShoppingListsDao(db : LocalDatabase) : ShoppingListsDao {
        return db.shoppingListDao()
    }

    @Singleton
    @Provides
    fun provideBusinessLogicRepository() : BusinessLogicRepository {
        return BusinessLogicRepository()
    }


}