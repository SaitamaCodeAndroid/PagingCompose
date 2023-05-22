package com.example.unsplash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unsplash.models.Image
import com.example.unsplash.models.UnsplashRemoteKeys

@Database(entities = [Image::class, UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDatabase: RoomDatabase() {

    abstract fun getImageDao(): UnsplashImageDao
    abstract fun getUnsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}
