package com.devwarex.news.ui.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.db.AppDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: AppDao
): ViewModel() {

    val articles = db.getArticles()

    fun updateArticle(
        url: String,
        isBooked: Boolean
    ) = viewModelScope.launch {
        db.updateArticle(url = url, isBooked = isBooked)
    }
}