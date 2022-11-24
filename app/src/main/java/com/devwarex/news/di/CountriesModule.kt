package com.devwarex.news.di

import com.devwarex.news.api.CountriesApiClient
import com.devwarex.news.api.CountriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class CountriesModule {

    @Provides
    @ViewModelScoped
    fun getCountriesApiService(): CountriesService = CountriesApiClient.create()

}