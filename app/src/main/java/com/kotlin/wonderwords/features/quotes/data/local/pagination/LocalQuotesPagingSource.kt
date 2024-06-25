//package com.kotlin.wonderwords.features.quotes.data.local.pagination
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
//import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
//import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory
//import kotlinx.coroutines.flow.first
//import javax.inject.Inject
//
//class LocalQuotesPagingSource @Inject constructor(
//    private val quotesDatabase: QuotesDatabase,
//    private val category: QuoteCategory
//) : PagingSource<Int, QuoteEntity>() {
//
//    override fun getRefreshKey(state: PagingState<Int, QuoteEntity>): Int? {
//        return state.anchorPosition?.let { position ->
//            state.closestPageToPosition(position)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteEntity> {
//        return try {
//
//            val currentPage = params.key ?: 1
//            val pageSize = params.loadSize
//
//            // Calculate offset for the query
//            val offset = (currentPage - 1) * pageSize
//            val data = quotesDatabase.quotesDao().getQuotesByCategory( category = category.name.lowercase(), limit = pageSize, offset = offset).first()
//            val totalCount = quotesDatabase.quotesDao().getQuotesCount()
//
//            // Determine next and previous keys
//            val nextKey = if (offset + data.size >= totalCount) null else currentPage + 1
//            val prevKey = if (currentPage == 1) null else currentPage - 1
//
//            LoadResult.Page(
//                data = data,
//                prevKey = prevKey,
//                nextKey = nextKey
//            )
//        } catch (exception: Exception) {
//            LoadResult.Error(exception)
//        }
//    }
//}