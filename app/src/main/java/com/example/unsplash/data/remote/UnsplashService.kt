package com.example.unsplash.data.remote

import com.example.unsplash.BuildConfig
import com.example.unsplash.models.Image
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashService {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("photos")
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<Image>

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("search/photos")
    suspend fun searchImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<Image>
}