package com.kotlin.wonderwords.features.quotes.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin.wonderwords.core.presentation.theme.WonderWordsTheme
import com.kotlin.wonderwords.features.quotes.domain.models.Quote

@Composable
fun QuoteItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    onQuoteClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onQuoteClick(quote.id!!) }
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.FormatQuote,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp).graphicsLayer {
                        rotationY = 180f
                    }
                )
                Icon(
                    imageVector = if (quote.favorite == true) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = null
                )
            }
            Text(
                text = quote.body ?: "null",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Rounded.FormatQuote,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(30.dp)
            )
            Text(
                text = "- ${quote.author}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuoteItemPreview() {
    WonderWordsTheme(
        darkTheme = false
    ) {
        QuoteItem(quote = Quote(
            id = 1,
            body = "This is a quote",
            author = "Author",
            favorite = false
        ), onQuoteClick = {})
    }
}