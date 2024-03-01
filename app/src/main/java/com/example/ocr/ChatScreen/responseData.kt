package com.example.ocr.ChatScreen

import com.google.gson.annotations.SerializedName

data class responseData(

    @SerializedName("id")
    val id: String,

    @SerializedName("object")
    val `object`: String,

    @SerializedName("created")
    val created: Long,

    @SerializedName("model")
    val model: String,

    @SerializedName("system_fingerprint")
    val systemFingerprint: String,

    @SerializedName("choices")
    val choices: List<Choice>,

    @SerializedName("usage")
    val usage: Usage
)


data class Choice(
    @SerializedName("index")
    val index: Int,

    @SerializedName("message")
    val message: messageData,

    @SerializedName("logprobs")
    val logprobs: Any?, // Adjust the type if logprobs have a specific structure

    @SerializedName("finish_reason")
    val finishReason: String
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,

    @SerializedName("completion_tokens")
    val completionTokens: Int,

    @SerializedName("total_tokens")
    val totalTokens: Int
)

