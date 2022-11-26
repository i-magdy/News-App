package com.devwarex.news.ui.onBoarding.country

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.db.Country
import com.devwarex.news.repos.AvailableCountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val datastoreImpl: DatastoreImpl,
    private val repo: AvailableCountriesRepo
): ViewModel() {

    init {
        viewModelScope.launch {
            repo.fetchCountries()
        }
    }
    private val _uiState = MutableStateFlow<CountryUiState>(CountryUiState())
    val uiState: StateFlow<CountryUiState> = _uiState

    fun updateSelectedCountry(
        code: String
    ) = viewModelScope.launch {
        Log.e("countr_code",code)
        datastoreImpl.updateCountry(code).asMap().size
    }

    fun getCountries() = viewModelScope.launch {
        datastoreImpl.appLanguage.collect { lang ->
            datastoreImpl.selectedCountry.collect { code ->
                repo.getCountryByCode(code).collect { country ->
                    if (lang == "ar") {
                        repo.countriesByArabic.collect { countries ->
                            _uiState.emit(
                                CountryUiState(
                                    countries = countries,
                                    selectedCountry = country,
                                    code = code
                                )
                            )
                        }
                    } else {
                        repo.countriesByEnglish.collect { countries ->
                            _uiState.emit(
                                CountryUiState(
                                    countries = countries,
                                    selectedCountry = country,
                                    code = code
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}