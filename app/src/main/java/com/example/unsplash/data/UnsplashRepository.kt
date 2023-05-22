package com.example.unsplash.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplash.data.local.UnsplashDatabase
import com.example.unsplash.data.remote.UnsplashService
import com.example.unsplash.models.Image
import com.example.unsplash.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashService,
    private val unsplashDatabase: UnsplashDatabase,
) {

    fun getAllImages(): Flow<PagingData<Image>> {
        val pagingSourceFactory = { unsplashDatabase.getImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashService = unsplashApi,
                unsplashDatabase = unsplashDatabase,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    /*fun searchImages(query: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow
    }*/

}
