package com.devwarex.news.models

import com.devwarex.news.models.news.ArticleModel

data class Articles(
    val category: String = "",
    val articles: List<ArticleModel> = emptyList(),
    val code: String = ""
)
