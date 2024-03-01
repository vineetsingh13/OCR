package com.example.ocr.ChatScreen

import com.google.gson.annotations.SerializedName

data class requestData(

    @SerializedName("model")
    val model: String,

    @SerializedName("messages")
    val messages: List<messageData>
)
