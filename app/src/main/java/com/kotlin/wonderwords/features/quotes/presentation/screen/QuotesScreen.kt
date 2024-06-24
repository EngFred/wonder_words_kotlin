package com.kotlin.wonderwords.features.quotes.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory
import com.kotlin.wonderwords.features.quotes.presentation.components.ErrorScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.LoadingScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.QuotesGrid
import com.kotlin.wonderwords.features.quotes.presentation.components.SearchTextField
import com.kotlin.wonderwords.features.quotes.presentation.viewModel.QuotesViewModel

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    onQuoteClick: (Int) -> Unit,
    quotesViewModel: QuotesViewModel = hiltViewModel()
) {

    val quotes = quotesViewModel.fetchQuotes.collectAsLazyPagingItems()
    val uiState = quotesViewModel.uiState.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchTextField(
            value = { uiState.searchQuery },
            onTextChange = {
                quotesViewModel.onEvent(QuotesUiEvents.SearchQueryChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CategoriesList(
            onCategorySelected = { category ->
                if (quotes.loadState.refresh !is LoadState.Loading) {
                    quotesViewModel.onEvent(QuotesUiEvents.SelectedCategory(category))
                }
            },
            selectedCategory = { uiState.selectedCategory }
        )

        when (quotes.loadState.refresh) {
            is LoadState.Error -> {
                ErrorScreen()
            }
            LoadState.Loading -> {
                LoadingScreen()
            }
            is LoadState.NotLoading -> {
                Spacer(modifier = Modifier.height(8.dp))
                QuotesGrid(modifier, quotes, onQuoteClick = onQuoteClick )
            }
        }

    }
}



@Composable
private fun CategoriesList(
    onCategorySelected: (QuoteCategory) -> Unit,
    selectedCategory: () -> QuoteCategory
) {
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        items(
            count = QuoteCategory.entries.size
        ) { index ->
            val category = QuoteCategory.entries[index]
            OutlinedButton(
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    contentColor = if (category == selectedCategory()) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = category.name)
            }
            Spacer(modifier = Modifier.width(6.dp))
        }

    }
}

