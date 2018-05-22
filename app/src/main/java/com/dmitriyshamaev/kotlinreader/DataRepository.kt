package com.dmitriyshamaev.kotlinreader

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.dmitriyshamaev.kotlinreader.model.NewsDatabase
import com.dmitriyshamaev.kotlinreader.model.NewsItem
import com.dmitriyshamaev.kotlinreader.network.NewsService
import com.dmitriyshamaev.kotlinreader.network.xml.RSSTikXmlConverterFactory
import com.dmitriyshamaev.kotlinreader.util.POJOConvertor
import retrofit2.Retrofit

class DataRepository private constructor(private val mExecutors: AppExecutors,
                                         private val mDatabase: NewsDatabase) {
    private val mObservableNews: MediatorLiveData<List<NewsItem>>

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    val news: LiveData<List<NewsItem>>
        get() = mObservableNews

    init {
        mObservableNews = MediatorLiveData()

        mObservableNews.addSource(mDatabase.newsDao().getAll()
        ) { newsItems ->
            mObservableNews.postValue(newsItems)
        }

        loadNewsItemsFromNetwork()
    }

    fun loadNewsItem(newsId: Long): LiveData<NewsItem> {
        return mDatabase.newsDao().getNewsEntity(newsId)
    }

    fun loadNewsItemsFromNetwork() {
        mExecutors.networkIO().execute {
            var rssResult = provideNewsService().getNews().execute()
            if (rssResult.isSuccessful) {
                var rss = rssResult.body()

                mExecutors.diskIO().execute {
                    val newNewsItems = POJOConvertor.toNewsItem(rss!!)
                    newNewsItems.forEach { item ->
                        mDatabase.newsDao().insert(item)
                    }
                }

            }
        }
    }

    private fun provideNewsService(): NewsService {
        return Retrofit.Builder()
                .baseUrl("http://feeds.rucast.net")
                .addConverterFactory(RSSTikXmlConverterFactory.create())
                .build()
                .create(NewsService::class.java)
    }

    companion object {

        private var INSTANCE: DataRepository? = null

        fun getInstance(executors: AppExecutors, database: NewsDatabase): DataRepository {
            if (INSTANCE == null) {
                synchronized(DataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DataRepository(executors, database)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}