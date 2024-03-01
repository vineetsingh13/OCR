package com.example.ocr.ChatScreen

import com.example.ocr.utils.url
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {


    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20,TimeUnit.SECONDS)
        .readTimeout(20,TimeUnit.SECONDS)
        .build()


    private val retrofit=Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}