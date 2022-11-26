package com.devwarex.news.ui.home.search

import android.util.Log
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.db.AppDao
import com.devwarex.news.db.Article
import com.devwarex.news.db.ArticleRelation
import com.devwarex.news.models.SearchedArticles
import com.devwarex.news.repos.SearchInArticlesRepo
import com.devwarex.news.util.ServerTimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRepo @Inject constructor(
    private val db: AppDao,
    private val datastore: DatastoreImpl,
    private val repo: SearchInArticlesRepo
) {
    val articles = MutableStateFlow<List<ArticleRelation>>(emptyList())
    val categories = db.getFollowedCategories()
    private val coroutine = CoroutineScope(Dispatchers.Default)
    private var category = "general"
    private var query = ""

    init {
        coroutine.launch {
            repo.articles.collect { saveArticles(data = it) }
        }
    }
    suspend fun search(
        query: String
    ){
        this.query = query
        datastore.selectedCountry.collect{ code ->
            repo.search(category = category, search = query, countryCode = code)
        }
    }

    fun setCategory(category: String){
        this.category = category
    }

    suspend fun updateArticle(
        url: String,
        isBooked: Boolean
    ){
        db.updateArticle(
            url = url,
            isBooked = isBooked
        )
    }

    private fun saveArticles(
        data: SearchedArticles
    ) = coroutine.launch {
        data.articles.forEach {
            if (it.url != null && it.url.isNotEmpty()) {
                Log.e("article",data.search+" size: "+data.articles.size)
                db.insertArticle(
                    Article(
                        url = it.url,
                        title = it.title ?: "",
                        description = it.description ?: "",
                        img = it.urlToImage ?: "",
                        author = it.author ?: "",
                        source = it.source.name ?: "",
                        category = data.category,
                        publishedAt = it.publishedAt?.let { d -> ServerTimeUtil.convertServerDate(d) }
                            ?: 0L,
                        isBooked = false,
                        isSearchedFor = true,
                        keyWord = data.search
                    )
                )
            }
        }
        launch {
            if (query.isNotEmpty()) {
                db.getSearchedArticles(query).collect {
                    articles.emit(it)
                }
            }
        }
    }

    fun cancelJob() = coroutine.cancel()
}