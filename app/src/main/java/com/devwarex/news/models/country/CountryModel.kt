package com.devwarex.news.models.country

data class CountryModel(
    val name: CountryNameModel,
    val cca2: String,
    val flag: String,
    val flags: CountryFlags,
    val translations: Map<String, CountryTranslations>
)