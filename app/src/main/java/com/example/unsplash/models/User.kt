package com.example.unsplash.models

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerializedName("links")
    @Embedded
    val userLinks: UserLinks,
    val username: String,
)
