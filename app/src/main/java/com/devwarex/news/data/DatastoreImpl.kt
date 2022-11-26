package com.devwarex.news.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "news_app_datastore")
class DatastoreImpl  @Inject constructor(
    private val context: Context
) {

    companion object{
        private const val APP_SELECTED_LANGUAGE: String = "user_preferred_language"
        private const val APP_ON_BOARD_STEP: String = "app_on_board_step"
        private const val SELECTED_COUNTRY: String = "selected_language"
        private const val REFRESH_TIMEOUT: String = "user_refresh_time"

        private val SELECTED_LANGUAGE_KEY = stringPreferencesKey(APP_SELECTED_LANGUAGE)
        private val ON_BOARD_STEP_KEY = intPreferencesKey(APP_ON_BOARD_STEP)
        private val SELECTED_COUNTRY_KEY = stringPreferencesKey(SELECTED_COUNTRY)
        private val REFRESH_TIMEOUT_KEY = longPreferencesKey(REFRESH_TIMEOUT)

    }

    val appLanguage: Flow<String> = context.datastore.data.map { it[SELECTED_LANGUAGE_KEY] ?: "" }
    val currentStep: Flow<Int> = context.datastore.data.map { it[ON_BOARD_STEP_KEY] ?: 1 }
    val selectedCountry: Flow<String> = context.datastore.data.map { it[SELECTED_COUNTRY_KEY] ?: "" }
    val refreshTimeout: Flow<Long> get() = context.datastore.data.map { it[REFRESH_TIMEOUT_KEY] ?: 0L }

    suspend fun updateRefreshTimeout(time: Long) = context.datastore.edit { it[REFRESH_TIMEOUT_KEY] = time }
    suspend fun updateAppLanguage(lang: String) = context.datastore.edit { it[SELECTED_LANGUAGE_KEY] = lang }
    suspend fun updateCurrentBoardStep(i: Int) = context.datastore.edit { it[ON_BOARD_STEP_KEY] = i }
    suspend fun updateCountry(country: String) = context.datastore.edit { it[SELECTED_COUNTRY_KEY] = country }

}