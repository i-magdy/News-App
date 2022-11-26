package com.devwarex.news.ui.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.data.DatastoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val datastore: DatastoreImpl
): ViewModel() {

    fun changeAppLanguage(
        lang: String
    ) = viewModelScope.launch {
        datastore.updateAppLanguage(lang)
    }
}