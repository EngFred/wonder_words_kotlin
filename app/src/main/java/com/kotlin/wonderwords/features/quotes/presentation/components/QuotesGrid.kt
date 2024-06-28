package com.kotlin.wonderwords.features.quotes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlin.wonderwords.features.quotes.domain.models.Quote

@Composable
fun QuotesGrid(
    modifier: Modifier,
    quotes: List<Quote>,
    onLoadMoreQuotes: () -> Unit,
    onQuoteClick: (Int) -> Unit
) {


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {

        items(
            count = quotes.size,
            key = {
                quotes[it].id!!
            },
        ) { index ->
            val quote = quotes[index]
            QuoteItem(quote = quote, onQuoteClick = onQuoteClick )
        }
        item {
            LaunchedEffect(quotes) {
                onLoadMoreQuotes()
            }
        }
    }

}