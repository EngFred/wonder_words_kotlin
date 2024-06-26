package com.kotlin.wonderwords.features.quotes.presentation.screen

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.theme.DarkSlateGrey
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode
import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory
import com.kotlin.wonderwords.features.quotes.presentation.components.ErrorScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.LoadingScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.QuotesGrid
import com.kotlin.wonderwords.features.quotes.presentation.components.SearchTextField
import com.kotlin.wonderwords.features.quotes.presentation.viewModel.QuotesViewModel
import com.kotlin.wonderwords.features.quotes.receiver.ConnectivityReceiver

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    onQuoteClick: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
    quotesViewModel: QuotesViewModel = hiltViewModel()
) {

    val quotes = quotesViewModel.fetchQuotes.collectAsLazyPagingItems()
    val uiState = quotesViewModel.uiState.collectAsState().value

    SetSystemBarColor(isAuth = false, sharedViewModel = sharedViewModel)

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
            selectedCategory = { uiState.selectedCategory },
            sharedViewModel = sharedViewModel
        )

        when (quotes.loadState.refresh) {
            is LoadState.Error -> {
                ErrorScreen()
            }
            LoadState.Loading -> {
                LoadingScreen()
            }
            is LoadState.NotLoading -> {
                if ( quotes.itemCount == 0 ) {
                    ErrorScreen(errorText = "No quotes found! Please make sure you have internet connection.")
                }else {
                    Spacer(modifier = Modifier.height(8.dp))
                    QuotesGrid(modifier, quotes, onQuoteClick = onQuoteClick )
                }
            }
        }

    }
}



@Composable
private fun CategoriesList(
    onCategorySelected: (QuoteCategory) -> Unit,
    selectedCategory: () -> QuoteCategory,
    sharedViewModel: SharedViewModel
) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value
    val clickableTextColor = if( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) Color.LightGray else Color.Black

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
                border = BorderStroke(
                    width = 1.1.dp,
                    color = if ( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) DarkSlateGrey else Color.Black
                ),
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    contentColor = if (category == selectedCategory()){
                        if ( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) {
                            Color.LightGray
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                Text(text = category.name)
            }
            Spacer(modifier = Modifier.width(6.dp))
        }

    }
}

