package com.example.ocr.ChatScreen


import com.example.ocr.utils.oa
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIRetrofitInterface {


    @POST("chat/completions")
    fun respone(
        @Body body: requestData,
        @Header("Content-Type") contentType: String="application/json",
        @Header("Authorization") authorization: String="Bearer $oa") : Call<responseData>
}