package com.devwarex.news.models.news

data class ArticleModel(
    val source: ArticleSource,
    val author: String?,
    val title: String,
    val description: String,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String
)
