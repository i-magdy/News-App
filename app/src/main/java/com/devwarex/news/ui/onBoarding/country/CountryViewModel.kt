package com.devwarex.news.ui.onBoarding.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.repos.AvailableCountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val datastoreImpl: DatastoreImpl,
    private val repo: AvailableCountriesRepo
): ViewModel() {

    private val _uiState = MutableStateFlow(CountryUiState())
    val uiState: StateFlow<CountryUiState> = _uiState

    init {
        viewModelScope.launch {
           repo.fetchCountries()
        }
    }

        fun updateSelectedCountry(
            code: String
        ) = viewModelScope.launch {
            datastoreImpl.updateCountry(code)
            getSelectedCountry()
        }

        fun getCountries() = viewModelScope.launch {
            datastoreImpl.appLanguage.collect { lang ->
                if (lang == "ar") {
                    repo.countriesByArabic.collect { countries ->
                        _uiState.emit(
                            CountryUiState(
                                countries = countries
                            )
                        )
                        getSelectedCountry()
                    }
                } else {
                    repo.countriesByEnglish.collect { countries ->
                        _uiState.emit(
                            CountryUiState(
                                countries = countries
                            )
                        )
                        getSelectedCountry()
                    }
                }
            }
        }

    fun next() = viewModelScope.launch { datastoreImpl.updateCurrentBoardStep(3) }

    private fun getSelectedCountry() = viewModelScope.launch {
        datastoreImpl.selectedCountry.collect { code ->
            repo.getCountryByCode(code).collect { country ->
                _uiState.emit(_uiState.value.copy(
                    selectedCountry = country,
                    code = code
                ))
            }
        }
    }
}