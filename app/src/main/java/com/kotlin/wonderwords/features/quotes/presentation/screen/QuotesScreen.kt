package com.kotlin.wonderwords.features.quotes.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin.wonderwords.features.quotes.presentation.viewModel.QuotesViewModel

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    onQuoteClick: () -> Unit,
    quotesViewModel: QuotesViewModel = hiltViewModel()
) {

    val quotes = quotesViewModel.fetchQuotes.collectAsLazyPagingItems()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quotes list",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            modifier = Modifier.clickable { onQuoteClick() }
        )
    }
}