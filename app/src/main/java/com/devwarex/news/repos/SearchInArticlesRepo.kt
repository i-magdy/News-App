package com.devwarex.news.repos

import android.util.Log
import com.devwarex.news.api.NewsApiService
import com.devwarex.news.di.NamedApiKey
import com.devwarex.news.models.SearchedArticles
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SearchInArticlesRepo @Inject constructor(
    private val service: NewsApiService,
    @NamedApiKey private val apiKey: String
) {

    val articles = MutableStateFlow(SearchedArticles())
    suspend fun search(
        countryCode: String,
        category: String,
        search: String
    ){
        try {
            val response = service.searchByCountryByCategory(
                apikey = apiKey,
                category = category,
                countryCode = countryCode,
                search = search
            )
            Log.e("response",response.isSuccessful.toString())
            if (response.isSuccessful){
                val body = response.body()
                if (body != null && body.status == "ok"){
                    articles.emit(
                        SearchedArticles(
                            category = category,
                            articles = body.articles,
                            search = search,
                            code = countryCode
                        )
                    )
                }
            }
        }catch (e: Exception){
            Log.e("articles_api",e.message.toString())
        }
    }


}