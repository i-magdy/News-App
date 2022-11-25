package com.devwarex.news.models.news

data class ArticlesModel(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleModel>
)
