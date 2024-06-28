package com.kotlin.wonderwords.features.quotes.data.remote.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuoteEntity
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import javax.inject.Inject

class AllQuotesPagingSource @Inject constructor(
    private val quotesApiService: QuotesApiService,
    private val category: QuoteCategory
) : PagingSource<Int, QuoteEntity>() {

    companion object {
        const val TAG = "AllQuotesPagingSource"
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
            val response = quotesApiService.getQuotes(page = currentPage)
            val data = response.quotes.filter { !it.body.isNullOrEmpty() && it.body.length > 4 }

            LoadResult.Page(
                data = data.map {
                     it.toQuoteEntity(category.name.lowercase())
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