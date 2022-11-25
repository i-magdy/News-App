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

    @Query("select * from categories")
    fun getCategories(): Flow<List<Category>>

    @Query("select COUNT(isFollowed) from categories")
    fun getFollowedCategoriesCount(): Flow<Int>

    @Query("select * from categories where isFollowed")
    fun getFollowedCategories(): Flow<List<Category>>

    @Update
    suspend fun updateCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(country: List<Category>)

}