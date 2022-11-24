package com.devwarex.news.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_table")
data class Country(
    @PrimaryKey val code: String,
    val name_ar: String,
    val name_en: String,
    val flagSymbol: String,
    val flagUrl: String,
    val selected: Boolean
)
