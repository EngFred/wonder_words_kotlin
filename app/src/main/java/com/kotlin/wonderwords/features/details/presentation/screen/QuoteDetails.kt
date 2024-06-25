package com.kotlin.wonderwords.features.details.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlin.wonderwords.features.details.presentation.common.DetailsAppbar
import com.kotlin.wonderwords.features.details.presentation.common.MainBody

@Composable
fun QuoteDetailsScreen(
    modifier: Modifier = Modifier,
    quoteId: Int
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(13.dp)
    ) {
        DetailsAppbar(
            onBack = { /*TODO*/ },
            upvotesCount = 11,
            downvotesCount = 0,
            favoritesCount = 2
        )
        MainBody(
            modifier = Modifier.weight(1f),
            body = "Never let you schooling interfere with your education",
            author = "Mark Twain updated"
        )
    }
}