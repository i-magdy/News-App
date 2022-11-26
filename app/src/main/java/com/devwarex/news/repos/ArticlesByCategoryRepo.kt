package com.devwarex.news.repos

import android.util.Log
import com.devwarex.news.api.NewsApiService
import com.devwarex.news.di.NamedApiKey
import com.devwarex.news.models.Articles
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ArticlesByCategoryRepo @Inject constructor(
    private val service: NewsApiService,
    @NamedApiKey val apiKey: String
    ) {
    val articles = MutableStateFlow(Articles())
    suspend fun sync(
        countryCode: String,
        category: String
    ){
        try {
            val response = service.getHeadlinesByCountryByCategory(
                apikey = apiKey,
                category = category,
                countryCode = countryCode
            )
            Log.e("response",response.isSuccessful.toString())
            if (response.isSuccessful){
                val body = response.body()
                if (body != null && body.status == "ok"){
                    articles.emit(Articles(
                        category = category,
                        articles = body.articles
                    ))
                }
            }
        }catch (e: Exception){
            Log.e("articles_api",e.message.toString())
        }
    }
}