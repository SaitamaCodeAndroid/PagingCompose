package com.example.unsplash.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    @SerializedName("regular")
    val regular: String,
)
