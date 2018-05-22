package com.dmitriyshamaev.kotlinreader.di

import com.dmitriyshamaev.kotlinreader.AppExecutors
import com.dmitriyshamaev.kotlinreader.DataRepository
import com.dmitriyshamaev.kotlinreader.model.NewsDatabase
import com.dmitriyshamaev.kotlinreader.viewmodel.NewsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val readerDIModule = applicationContext {
    bean { NewsDatabase.getInstance(androidApplication()) }
    bean { DataRepository.getInstance(AppExecutors(), get()) }
    bean { NewsListViewModel(get()) }
}