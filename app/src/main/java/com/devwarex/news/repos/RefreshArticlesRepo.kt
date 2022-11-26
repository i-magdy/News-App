package com.devwarex.news.repos

import com.devwarex.news.db.AppDao
import com.devwarex.news.db.Article
import com.devwarex.news.models.Articles
import com.devwarex.news.util.ServerTimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RefreshArticlesRepo @Inject constructor(
    private val db: AppDao,
    private val articlesRepo: ArticlesByCategoryRepo
) {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    init {
        coroutine.launch {
            articlesRepo.articles.collect{
                if (it.articles.isNotEmpty() &&
                    it.category.isNotEmpty()){
                    saveArticles(it)
                }
            }
        }
    }
    fun sync(
        countryCode: String
    ) = coroutine.launch {
        db.getFollowedCategories()
            .collect{ categories ->
                categories.forEach {
                    articlesRepo.sync(
                        countryCode = countryCode,
                        category = it.value
                    )
                }
            }
    }

    private fun saveArticles(
        data: Articles
    ) = coroutine.launch{
        data.articles.forEach {
            if (it.url != null && it.url.isNotEmpty())
            db.insertArticle(
                Article(
                    url = it.url,
                    title = it.title,
                    description = it.description,
                    img = it.urlToImage ?: "",
                    author = it.author ?: "",
                    source = it.source.name,
                    category = data.category,
                    publishedAt = ServerTimeUtil.convertServerDate(it.publishedAt),
                    isBooked = false,
                    isSearchedFor = false,
                    keyWord = ""
                )
            )
        }
    }

    fun cancelJob() = coroutine.cancel()
}