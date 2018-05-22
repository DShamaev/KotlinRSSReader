package com.dmitriyshamaev.kotlinreader.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.dmitriyshamaev.kotlinreader.DataRepository
import com.dmitriyshamaev.kotlinreader.model.NewsItem

class NewsListViewModel(val repository: DataRepository): ViewModel() {
    private val mObservableNews: MediatorLiveData<List<NewsItem>>

    val news: LiveData<List<NewsItem>>
        get() = mObservableNews

    init {
        mObservableNews = MediatorLiveData()
        // set by default null, until we get data from the database.
        mObservableNews.value = null

        // observe the changes of the products from the database and forward them
        mObservableNews.addSource(repository.news, Observer<List<NewsItem>> { mObservableNews.setValue(it) })

    }

    fun getNewsItem(id: Long): LiveData<NewsItem> {
        return repository.loadNewsItem(id)
    }

}