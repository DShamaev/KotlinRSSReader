package com.dmitriyshamaev.kotlinreader.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

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