package com.example.ocr.ChatScreen

import com.example.ocr.utils.url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {



    private val retrofit=Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}