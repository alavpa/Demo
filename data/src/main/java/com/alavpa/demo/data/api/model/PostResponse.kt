package com.alavpa.demo.data.api.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("userId") val userId: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("body") val body: String? = null
)
