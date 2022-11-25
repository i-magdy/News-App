package com.devwarex.news.api

import com.devwarex.news.models.country.CountryModel
import retrofit2.Response
import retrofit2.http.GET

interface CountriesService {

    @GET("v3.1/all")
    suspend fun getAllCountriesData(): Response<List<CountryModel>>

}