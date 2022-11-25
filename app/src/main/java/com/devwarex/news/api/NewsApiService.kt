package com.devwarex.news.api

import com.devwarex.news.models.news.ArticlesModel
import com.devwarex.news.util.EndPoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {

    @GET(EndPoints.TOP_HEADLINE)
    suspend fun getHeadlinesByCountryInAllCategories(
        @Header(EndPoints.X_API_KEY) apikey: String,
        @Query(EndPoints.QUERY_COUNTRY) countryCode: String
    ): Response<ArticlesModel>

    @GET(EndPoints.TOP_HEADLINE)
    suspend fun getHeadlinesByCountryByCategory(
        @Header(EndPoints.X_API_KEY) apikey: String,
        @Query(EndPoints.QUERY_COUNTRY) countryCode: String,
        @Query(EndPoints.QUERY_CATEGORY) category: String
    ): Response<ArticlesModel>

    @GET(EndPoints.TOP_HEADLINE)
    suspend fun searchByCountryByCategory(
        @Header(EndPoints.X_API_KEY) apikey: String,
        @Query(EndPoints.QUERY_SEARCH) search: String,
        @Query(EndPoints.QUERY_COUNTRY) countryCode: String,
        @Query(EndPoints.QUERY_CATEGORY) category: String
    ): Response<ArticlesModel>

    @GET(EndPoints.TOP_HEADLINE)
    suspend fun searchByCountryInAllCategories(
        @Header(EndPoints.X_API_KEY) apikey: String,
        @Query(EndPoints.QUERY_SEARCH) search: String,
        @Query(EndPoints.QUERY_COUNTRY) countryCode: String
    ): Response<ArticlesModel>

}