package com.example.unsplash.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.unsplash.data.local.UnsplashDatabase
import com.example.unsplash.data.remote.UnsplashService
import com.example.unsplash.models.Image
import com.example.unsplash.models.UnsplashRemoteKeys
import com.example.unsplash.utils.Constants.ITEMS_PER_PAGE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator @Inject constructor(
    private val unsplashService: UnsplashService,
    private val unsplashDatabase: UnsplashDatabase,
): RemoteMediator<Int, Image>() {

    private val imageDao = unsplashDatabase.getImageDao()
    private val unsplashRemoteKeysDao = unsplashDatabase.getUnsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Image>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = unsplashService.getAllImages(
                page = currentPage,
                perPage = ITEMS_PER_PAGE,
            )
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            unsplashDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imageDao.deleteAllImages()
                    unsplashRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { Image ->
                    UnsplashRemoteKeys(
                        id = Image.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                    )
                }
                unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                imageDao.addImages(images = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Image>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Image>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { Image ->
                unsplashRemoteKeysDao.getRemoteKeys(id = Image.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Image>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { Image ->
                unsplashRemoteKeysDao.getRemoteKeys(id = Image.id)
            }
    }
}
