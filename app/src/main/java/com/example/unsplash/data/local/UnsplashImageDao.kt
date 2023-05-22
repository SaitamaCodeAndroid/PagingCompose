package com.example.unsplash.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unsplash.models.Image

@Dao
interface UnsplashImageDao {

    @Query("SELECT * FROM UNSPLASH_IMAGE_TABLE")
    fun getAllImages(): PagingSource<Int, Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<Image>)

    @Query("DELETE FROM UNSPLASH_IMAGE_TABLE")
    suspend fun deleteAllImages()
}
