package com.devwarex.news.ui.onBoarding.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devwarex.news.data.DatastoreImpl
import com.devwarex.news.repos.CategoriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repo: CategoriesRepo,
    private val datastore: DatastoreImpl
): ViewModel() {

    private var _count = -1
    init {
        viewModelScope.launch {
            launch { repo.insertCategories() }
            launch { repo.followedCategories.collect{ _count = it } }
        }
    }

    val categories = repo.categories
    val count = repo.followedCategories

    fun updateSubscribedCategory(
        value: String,
        isChecked: Boolean
    ) = viewModelScope.launch {
        if (isChecked){
            if (_count < 3) {
                repo.updateCategory(
                    value = value,
                    isChecked = true
                )
            }
        }else{
            repo.updateCategory(
                value = value,
                isChecked = false
            )
        }
    }

    fun finish() = viewModelScope.launch {
        if (_count == 3) {
            datastore.updateCurrentBoardStep(4)
        }
    }
}