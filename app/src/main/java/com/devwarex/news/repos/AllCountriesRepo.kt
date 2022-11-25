package com.devwarex.news.repos

import com.devwarex.news.api.CountriesService
import com.devwarex.news.models.country.CountryModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AllCountriesRepo @Inject constructor(
    private val service: CountriesService
) {

    fun getCountries() = flow<List<CountryModel>> {
        try {
            val response = service.getAllCountriesData()
            if (response.isSuccessful) {
                if (response.body().isNullOrEmpty()) {
                    emit(emptyList())
                } else {
                    emit(response.body()!!)
                }
            } else {
                emit(emptyList())
            }
        }catch (e: Exception){
            emit(emptyList())
        }
    }
}