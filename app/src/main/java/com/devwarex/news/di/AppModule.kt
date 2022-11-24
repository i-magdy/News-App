package com.devwarex.news.di

import android.content.Context
import com.devwarex.news.data.DatastoreImpl
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

}