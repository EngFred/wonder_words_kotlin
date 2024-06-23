package com.kotlin.wonderwords.features.quotes.data.source.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuote
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.data.source.api.QuotesApiService
import javax.inject.Inject

class QuotesPagingSource @Inject constructor(
    private val quotesApiService: QuotesApiService
) : PagingSource<Int, QuoteDTO>() {

    override fun getRefreshKey(state: PagingState<Int, QuoteDTO>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteDTO> {
        return try {
            val currentPage = params.key ?: 1
            val response = quotesApiService.getQuotes(page = currentPage)
            val data = response.quotes

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.lastPage) null else currentPage + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


}