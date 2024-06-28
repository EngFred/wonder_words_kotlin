package com.kotlin.wonderwords.features.quotes.data.remote.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuoteEntity
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

class QuotesByCategoryPagingSource @Inject constructor(
    private val quotesApiService: QuotesApiService,
    private val category: String
) : PagingSource<Int, QuoteEntity>() {

    companion object {
        const val TAG = "QuotesByCategoryPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, QuoteEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteEntity> {
        return try {
            val currentPage = params.key ?: 1
            val response = quotesApiService.getQuotesByCategory(currentPage, category)
            val data = response.quotes.filter { !it.body.isNullOrEmpty() && it.body.length > 4 }

            LoadResult.Page(
                data = data.map {
                    it.toQuoteEntity( category )
                },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.lastPage) null else currentPage + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Unexpected error loading quotes", exception)
            LoadResult.Error(exception)
        }
    }

}