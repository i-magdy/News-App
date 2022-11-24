package com.devwarex.news.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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
        private val SELECTED_LANGUAGE_KEY = stringPreferencesKey(APP_SELECTED_LANGUAGE)
        private val ON_BOARD_STEP_KEY = intPreferencesKey(APP_ON_BOARD_STEP)
    }

    val appLanguage: Flow<String> = context.datastore.data.map { it[SELECTED_LANGUAGE_KEY] ?: "" }
    val currentStep: Flow<Int> = context.datastore.data.map { it[ON_BOARD_STEP_KEY] ?: -1 }

    suspend fun updateAppLanguage(lang: String) = context.datastore.edit { it[SELECTED_LANGUAGE_KEY] = lang }
    suspend fun updateCurrentBoardStep(i: Int) = context.datastore.edit { it[ON_BOARD_STEP_KEY] = i }

}