package com.dmitriyshamaev.kotlinreader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dmitriyshamaev.kotlinreader.model.NewsDatabase
import com.dmitriyshamaev.kotlinreader.model.NewsItem
import com.dmitriyshamaev.kotlinreader.network.NewsService
import com.dmitriyshamaev.kotlinreader.network.xml.RSSTikXmlConverterFactory
import com.dmitriyshamaev.kotlinreader.util.POJOConverter
import retrofit2.Retrofit


class DataRepository private constructor(private val mExecutors: AppExecutors,
                                         private val mDatabase: NewsDatabase,
                                         private val appStorage: AppStorage) {
    private val mObservableNews: MediatorLiveData<List<NewsItem>> = MediatorLiveData()

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    val news: LiveData<List<NewsItem>>
        get() = mObservableNews

    init {

        mObservableNews.addSource(mDatabase.newsDao().getAll()
        ) { newsItems ->
            mObservableNews.postValue(newsItems)
        }

        loadNewsItemsFromNetwork()
    }

    fun loadNewsItem(newsId: Long): LiveData<NewsItem> {
        return mDatabase.newsDao().getNewsEntity(newsId)
    }

    fun loadNewsItemsFromUrl(url: String) {
        if (url === "") return
        if (appStorage.rssUrl == url) return

        removeAllItems()
        appStorage.rssUrl = url
        loadNewsItemsFromNetwork()
    }

    fun loadNewsItemsFromNetwork() {
        mExecutors.networkIO().execute {
            val rssResult = provideNewsService().getNews(appStorage.rssUrl).execute()
            if (rssResult.isSuccessful) {
                val rss = rssResult.body()

                if (rss != null) {
                    mExecutors.diskIO().execute {
                        val newNewsItems = POJOConverter.toNewsItem(rss!!)
                        newNewsItems.forEach { item ->
                            mDatabase.newsDao().insert(item)
                        }
                    }
                }
            }
        }
    }

    private fun removeAllItems() {
        mExecutors.diskIO().execute {
            mDatabase.newsDao().deleteAll()
        }
    }

    private fun provideNewsService(): NewsService {
        return Retrofit.Builder()
                .baseUrl("http://google.com")
                .addConverterFactory(RSSTikXmlConverterFactory.create())
                .build()
                .create(NewsService::class.java)
    }

    companion object {

        private var INSTANCE: DataRepository? = null

        fun getInstance(executors: AppExecutors, database: NewsDatabase, appStorage: AppStorage): DataRepository {
            if (INSTANCE == null) {
                synchronized(DataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DataRepository(executors, database, appStorage)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}