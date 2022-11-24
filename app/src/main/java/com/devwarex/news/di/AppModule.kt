package com.devwarex.news.di

import android.content.Context
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.db.AppDao
import com.devwarex.news.db.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppDatastoreImpl(@ApplicationContext context: Context) = DatastoreImpl(context)

    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context: Context): AppDao = AppRoomDatabase.getInstance(context).appDbDao()

}