package com.kotlin.wonderwords.features.details.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.common.ErrorScreen
import com.kotlin.wonderwords.core.presentation.common.LoadingScreen
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.details.presentation.common.DetailsAppbar
import com.kotlin.wonderwords.features.details.presentation.common.MainBody
import com.kotlin.wonderwords.features.details.presentation.viewModel.QuoteDetailViewModel
import com.kotlin.wonderwords.features.details.utils.shareQuote

@Composable
fun QuoteDetailsScreen(
    modifier: Modifier = Modifier,
    quoteId: Int,
    onBack: () -> Unit,
    sharedViewModel: SharedViewModel,
    quoteDetailsViewModel: QuoteDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        Log.d("TAG", "$quoteId")
    }

    val uiState = quoteDetailsViewModel.uiState.collectAsState().value
    val context = LocalContext.current

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value

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
                ErrorScreen(
                    errorText = uiState.error,
                    onRetry = {
                        quoteDetailsViewModel.onEvent(QuoteDetailEvents.RetryClicked)
                    }
                )
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
                        onShare = {
                            shareQuote(context, quote)
                        },
                        favorited = quote.userDetails?.favorited ?: false,
                        upvoted = quote.userDetails?.upvoted ?: false,
                        downVoted = quote.userDetails?.downvoted ?: false,
                        currentTheme = currentTheme
                    )
                    MainBody(
                        modifier = Modifier.weight(1f),
                        quote = quote
                    )
                } else{
                    ErrorScreen( errorText = "Quote not found!",
                        onRetry = {
                            quoteDetailsViewModel.onEvent(QuoteDetailEvents.RetryClicked)
                        })
                }

            }
        }

    }
}