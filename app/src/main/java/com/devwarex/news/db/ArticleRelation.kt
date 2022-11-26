package com.devwarex.news.db

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleRelation(
    @Embedded val article: Article,
    @Relation(
        entity = Category::class,
        parentColumn = "category",
        entityColumn = "value"
    )
    val category: Category?
)
