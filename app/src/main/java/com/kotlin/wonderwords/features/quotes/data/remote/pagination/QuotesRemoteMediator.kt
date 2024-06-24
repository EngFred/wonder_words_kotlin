package com.kotlin.wonderwords.features.quotes.data.remote.pagination

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuoteEntity
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.data.modals.RemoteKey
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuotesRemoteMediator @Inject constructor(
    private val quotesApiService: QuotesApiService,
    private val quotesDatabase: QuotesDatabase
) : RemoteMediator<Int, QuoteEntity>() {

    companion object {
        const val TAG = "QuotesRemoteMediator"
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, QuoteEntity>
    ): MediatorResult {

        return  try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(endOfPaginationReached = (remoteKey != null))
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = (remoteKey != null))
                    nextPage
                }
            }

            val apiResponse = quotesApiService.getQuotes(currentPage)
            val quotes = apiResponse.quotes.filter { !it.body.isNullOrEmpty() }
            val endOfPaginationReached = apiResponse.lastPage

            val prevPage = if (currentPage == 1) null else (currentPage - 1)
            val nextPage = if (endOfPaginationReached) null else (currentPage + 1)

            quotesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    quotesDatabase.quotesDao().deleteQuotes()
                    quotesDatabase.remoteKeysDao().deleteRemoteKeys()
                }

                val remoteKeys = quotes.map {
                    RemoteKey(
                        id = it.id!!,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                quotesDatabase.quotesDao().addQuotes(
                    quotes.map {
                        it.toQuoteEntity()
                    }
                )

                Log.d(TAG, "Quotes have been cached")
                quotesDatabase.remoteKeysDao().addRemoteKeys(remoteKeys)

                Log.d(TAG, "Remote keys have been cached")
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, QuoteEntity>
    ) : RemoteKey? = state.anchorPosition?.let {
        state.closestItemToPosition( it )?.id?.let { remoteKeyId ->
            quotesDatabase.remoteKeysDao().getRemoteKeyById( remoteKeyId )
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, QuoteEntity>
    ) : RemoteKey? = state.pages.firstOrNull{
        it.data.isNotEmpty()
    }?.firstOrNull()?.let {
        quotesDatabase.remoteKeysDao().getRemoteKeyById(it.id)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, QuoteEntity>
    ) : RemoteKey? = state.pages.lastOrNull {
        it.data.isNotEmpty()
    }?.data?.lastOrNull()?.let {
        quotesDatabase.remoteKeysDao().getRemoteKeyById( it.id )
    }


}