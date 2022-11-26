package com.devwarex.news.repos

import com.devwarex.news.data.Categories
import com.devwarex.news.db.AppDao
import com.devwarex.news.db.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepo @Inject constructor(
    private val db: AppDao
) {

    val followedCategories: Flow<Int> = db.getFollowedCategoriesCount()
    val categories: Flow<List<Category>> = db.getCategories()

    suspend fun updateCategory(value: String,isChecked: Boolean) = db.updateCategory(value,isChecked)

    suspend fun insertCategories() = db.insertCategory(Categories.availableCategories)

}