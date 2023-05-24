package com.example.unsplash.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.unsplash.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class HomeViewModel
@Inject constructor(repository: UnsplashRepository): ViewModel() {
    val allImages = repository.getAllImages()
}