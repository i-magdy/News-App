package com.devwarex.news.repos

import com.devwarex.news.data.Countries
import com.devwarex.news.db.AppDao
import com.devwarex.news.db.Country
import javax.inject.Inject

class AvailableCountriesRepo @Inject constructor(
    private val db: AppDao,
    private val countriesRepo: AllCountriesRepo
) {

    suspend fun fetchCountries(){
        countriesRepo.getCountries().collect{
            it.forEach { country ->
                if (Countries.availableCountries.contains(country.cca2)){
                    saveCountry(
                        country = Country(
                            code = country.cca2,
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