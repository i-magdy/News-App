package com.devwarex.news.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val value: String,
    val name_ar: String,
    val name_en:String,
    val isFollowed: Boolean
)
