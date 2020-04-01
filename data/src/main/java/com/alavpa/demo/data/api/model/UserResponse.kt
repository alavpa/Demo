package com.alavpa.demo.data.api.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("website") val website: String? = null
)
