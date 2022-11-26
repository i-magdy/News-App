package com.devwarex.news.ui.onBoarding.country

import com.devwarex.news.db.Country

data class CountryUiState(
    val countries: List<Country> = emptyList(),
    val code: String = "",
    val selectedCountry: Country? = null
)
