package com.dmitriyshamaev.kotlinreader.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * from newsItem")
    fun getAll(): LiveData<List<NewsItem>>

    @Query("SELECT * from newsItem WHERE id = :id")
    fun getNewsEntity(id: Long): LiveData<NewsItem>

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Query("DELETE from newsItem")
    fun deleteAll()
}