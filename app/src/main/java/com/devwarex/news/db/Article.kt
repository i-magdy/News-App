package com.devwarex.news.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_table")
data class Article(
    @PrimaryKey val url: String,
    val title: String,
    val description: String,
    val img: String,
    val publishedAt: Long,
    val author: String,
    val source: String,
    val category: String,
    val isBooked: Boolean,
    val isSearchedFor: Boolean,
    val keyWord: String,
    val countryCode: String
)
