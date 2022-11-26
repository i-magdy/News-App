package com.devwarex.news.di

import android.content.Context
import com.devwarex.news.R
import com.devwarex.news.api.NewsApiClient
import com.devwarex.news.api.NewsApiService
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
    fun provideAppDatastoreImpl(@ApplicationContext context: Context) = DatastoreImpl(context)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDao = AppRoomDatabase.getInstance(context).appDbDao()

    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService = NewsApiClient.create()

    @Provides
    @Singleton
    @NamedApiKey
    fun provideNewsApiKey(@ApplicationContext context: Context): String = context.getString(R.string.api_key)

}