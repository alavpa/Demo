package com.alavpa.demo.data.api.model

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("postId") val postId: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("body") val body: String? = null
)
