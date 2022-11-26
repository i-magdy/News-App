package com.devwarex.news.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("select * from countries_table")
    fun getCountries(): Flow<List<Country>>

    @Query("select * from countries_table order by name_ar")
    fun getCountriesByArabic(): Flow<List<Country>>

    @Query("select * from countries_table order by name_en")
    fun getCountriesByEnglish(): Flow<List<Country>>

    @Query("select * from countries_table where code = :code")
    fun getCountryByCode(code: String): Flow<Country>

    @Query("select * from categories")
    fun getCategories(): Flow<List<Category>>

    @Query("select COUNT(value) from categories where isFollowed")
    fun getFollowedCategoriesCount(): Flow<Int>

    @Query("select * from categories where isFollowed")
    fun getFollowedCategories(): Flow<List<Category>>

    @Query("update categories set isFollowed = :isChecked where value = :value")
    suspend fun updateCategory(value: String,isChecked: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(country: List<Category>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: Article)
}