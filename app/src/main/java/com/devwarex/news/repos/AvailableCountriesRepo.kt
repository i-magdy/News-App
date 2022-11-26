package com.devwarex.news.repos

import android.util.Log
import com.devwarex.news.data.Countries
import com.devwarex.news.db.AppDao
import com.devwarex.news.db.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AvailableCountriesRepo @Inject constructor(
    private val db: AppDao,
    private val countriesRepo: AllCountriesRepo
) {

    val countriesByArabic: Flow<List<Country>> get() = db.getCountriesByArabic()
    val countriesByEnglish: Flow<List<Country>> get() = db.getCountriesByEnglish()
    fun getCountryByCode(code: String): Flow<Country> = db.getCountryByCode(code)

    suspend fun fetchCountries(){
        countriesRepo.getCountries().collect{
            it.forEach { country ->
                if (Countries.availableCountries.contains(country.cca2.lowercase())){
                    saveCountry(
                        country = Country(
                            code = country.cca2.lowercase(),
                            name_en = country.name.common,
                            name_ar = country.translations["ara"]?.common ?: "",
                            flagSymbol = country.flag,
                            flagUrl = country.flags.png,
                            selected = false
                        )
                    )
                }
            }
        }
    }

    private suspend fun saveCountry(
        country: Country
    ){
        db.insertCountry(country = country)
    }

}