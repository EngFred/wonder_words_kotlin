package com.kotlin.wonderwords.features.quotes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.kotlin.wonderwords.features.quotes.domain.domain.Quote

@Composable
fun QuotesGrid(
    modifier: Modifier,
    quotes: LazyPagingItems<Quote>,
    onQuoteClick: (Int) -> Unit
) {


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {

        items(
            count = quotes.itemCount,
            key = quotes.itemKey { it.id!! },
        ) { index ->
            val quote = quotes[index]
            quote?.let {
                QuoteItem(quote = it, onQuoteClick = onQuoteClick )
            }
        }
    }

}