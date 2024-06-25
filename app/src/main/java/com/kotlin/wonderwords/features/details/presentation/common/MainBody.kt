package com.kotlin.wonderwords.features.details.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainBody(
    body: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.FormatQuote,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Start)
                .size(50.dp)
                .graphicsLayer {
                    rotationY = 180f
                }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = body,
                fontSize = 22.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
        Icon(
            imageVector = Icons.Rounded.FormatQuote,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .size(50.dp)
        )
        Text(
            text = "- $author",
            modifier = Modifier.align(Alignment.End)
        )
    }
}