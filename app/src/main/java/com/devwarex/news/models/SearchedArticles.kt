package com.devwarex.news.models

import com.devwarex.news.models.news.ArticleModel

data class SearchedArticles(
    val category: String = "",
    val articles: List<ArticleModel> = emptyList(),
    val search: String = "",
    val code: String = ""
)
