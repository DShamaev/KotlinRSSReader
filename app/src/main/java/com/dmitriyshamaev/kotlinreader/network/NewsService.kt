package com.dmitriyshamaev.kotlinreader.network

import com.dmitriyshamaev.kotlinreader.network.pojo.RSS
import retrofit2.Call
import retrofit2.http.GET


interface NewsService {
    @GET("radio-t")
    fun getNews(): Call<RSS>
}