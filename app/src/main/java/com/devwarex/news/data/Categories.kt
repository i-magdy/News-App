package com.devwarex.news.data

import com.devwarex.news.db.Category

object Categories {
    val availableCategories = ArrayList<Category>()
        .apply {
            add(Category(
                value = "business",
                name_ar = "أعمال",
                name_en = "Business",
                isFollowed = false
            ))
            add(Category(
                value = "entertainment",
                name_ar = "ترفيه",
                name_en = "Entertainment",
                isFollowed = false
            ))
            add(Category(
                value = "general",
                name_ar = "عام",
                name_en = "General",
                isFollowed = false
            ))
            add(Category(
                value = "health",
                name_ar = "صحه",
                name_en = "Health",
                isFollowed = false
            ))
            add(Category(
                value = "science",
                name_ar = "علوم",
                name_en = "Science",
                isFollowed = false
            ))
            add(Category(
                value = "sports",
                name_ar = "رياضه",
                name_en = "Sports",
                isFollowed = false
            ))
            add(Category(
                value = "technology",
                name_ar = "تكنولوجيا",
                name_en = "Technology",
                isFollowed = false
            ))
        }
}