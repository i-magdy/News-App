package com.devwarex.news.ui.onBoarding.wecome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.data.DatastoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val datastore: DatastoreImpl
): ViewModel() {

    val language: Flow<String> = datastore.appLanguage

    fun changeAppLanguage(
        lang: String
    ) = viewModelScope.launch {
        datastore.updateAppLanguage(lang)
    }

    fun start() = viewModelScope.launch { datastore.updateCurrentBoardStep(2) }
    fun updateCountry(country: String) = viewModelScope.launch {
        datastore.selectedCountry.collect { currentCountry ->
            if (currentCountry.isEmpty()) {
                datastore.updateCountry(country)
            }
        }
    }
}