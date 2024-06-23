package com.kotlin.wonderwords.features.quotes.data.source.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.data.source.api.QuotesApiService
import javax.inject.Inject

class QuotesByCategoryPagingSource @Inject constructor(
    private val quotesApiService: QuotesApiService,
    private val category: String
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
            val response = quotesApiService.getQuotesByCategory(currentPage, category)
            val data = response.quotes.filter { !it.body.isNullOrEmpty() }

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