package com.erdees.netgurushoppinglist.di

import android.app.Activity
import android.content.Context
import com.erdees.netgurushoppinglist.view.activities.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
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



}