package com.example.ocr.ChatScreen

import com.google.gson.annotations.SerializedName

data class requestData(

    @SerializedName("model")
    val model:String,

    @SerializedName("messages")
    val message: List<messages>
)

data class messages(

    @SerializedName("role")
    val role:String,

    @SerializedName("content")
    val content: String
)
