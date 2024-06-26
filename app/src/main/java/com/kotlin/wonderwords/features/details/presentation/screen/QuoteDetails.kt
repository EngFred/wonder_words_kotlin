package com.kotlin.wonderwords.features.details.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.features.details.presentation.common.DetailsAppbar
import com.kotlin.wonderwords.features.details.presentation.common.MainBody
import com.kotlin.wonderwords.features.details.presentation.viewModel.QuoteDetailViewModel
import com.kotlin.wonderwords.features.quotes.presentation.components.ErrorScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.LoadingScreen

@Composable
fun QuoteDetailsScreen(
    modifier: Modifier = Modifier,
    quoteId: Int,
    onBack: () -> Unit,
    quoteDetailsViewModel: QuoteDetailViewModel = hiltViewModel()
) {

    val uiState = quoteDetailsViewModel.uiState.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(13.dp)
    ) {

        when {
            uiState.isLoading -> {
                LoadingScreen()
            }
            uiState.error != null -> {
                ErrorScreen( errorText = "Whops! Something went wrong.")
            }

            else -> {

                val quote = uiState.quote

                if (quote != null) {
                    DetailsAppbar(
                        onBack = onBack,
                        upvotesCount = quote.upvotesCount ?: 0,
                        downvotesCount = quote.downvotesCount ?: 0,
                        favoritesCount = quote.favoritesCount ?: 0,
                        onFavClick = {
                            quoteDetailsViewModel.onEvent(QuoteDetailEvents.FavClicked)
                        },
                        onUpvoteClick = {
                             quoteDetailsViewModel.onEvent(QuoteDetailEvents.Upvoted)
                        },
                        onDowvoteClick = {
                            quoteDetailsViewModel.onEvent(QuoteDetailEvents.Downvoted)
                        },
                        isFavorite = quote.userDetails?.favorited ?: false
                    )
                    MainBody(
                        modifier = Modifier.weight(1f),
                        body = quote.body ?: "---",
                        author = quote.author ?: "--"
                    )
                } else{
                    ErrorScreen( errorText = "Unable to find quote!")
                }

            }
        }

    }
}