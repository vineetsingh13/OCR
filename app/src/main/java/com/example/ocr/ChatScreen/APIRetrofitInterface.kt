package com.example.ocr.ChatScreen

import com.example.ocr.utils.openAI_key
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIRetrofitInterface {


    @POST("completions")
    fun respone(
        @Body body: requestData,
        @Header("Content-Type") contentType: String="application/json",
        @Header("Authorization") authorization: String="Bearer $openAI_key") : Call<responseData>
}