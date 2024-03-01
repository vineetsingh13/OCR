package com.example.ocr.ChatScreen

import com.google.gson.annotations.SerializedName

data class messageData(

    @SerializedName("role")
    val role: String,

    @SerializedName("content")
    val content: String

)
