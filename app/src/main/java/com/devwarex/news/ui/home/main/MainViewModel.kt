package com.devwarex.news.ui.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.db.AppDao
import com.devwarex.news.db.ArticleRelation
import com.devwarex.news.repos.RefreshArticlesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: AppDao,
    private val refreshArticlesRepo: RefreshArticlesRepo,
    private val datastore: DatastoreImpl
): ViewModel() {

    private val _articles = MutableStateFlow<List<ArticleRelation>>(emptyList())
    val articles: StateFlow<List<ArticleRelation>> = _articles

    init {
        viewModelScope.launch {
            datastore.selectedCountry.collect{ country ->
                db.getArticlesByCountry(
                    code = country
                ).collect{ if (it.isNotEmpty()) _articles.emit(it) else  refreshArticlesRepo.sync(country) }
            }
        }
    }
    val categoriesCount = db.getFollowedCategoriesCount()

    fun updateArticle(
        url: String,
        isBooked: Boolean
    ) = viewModelScope.launch {
        db.updateArticle(url = url, isBooked = isBooked)
    }
}