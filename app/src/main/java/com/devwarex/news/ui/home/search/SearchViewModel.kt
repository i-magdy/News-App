package com.devwarex.news.ui.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.db.ArticleRelation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepo
): ViewModel(){

    val articles: StateFlow<List<ArticleRelation>> = repo.articles
    fun search(text: String) = viewModelScope.launch {
        repo.search(query = text)
    }

    fun updateArticle(
        url: String,
        isBooked: Boolean
    ) = viewModelScope.launch {
        repo.updateArticle(url = url, isBooked = isBooked)
    }

    override fun onCleared() {
        super.onCleared()
        repo.cancelJob()
    }
}