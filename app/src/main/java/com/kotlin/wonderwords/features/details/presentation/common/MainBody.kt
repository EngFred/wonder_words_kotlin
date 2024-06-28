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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin.wonderwords.core.presentation.theme.playWrite
import com.kotlin.wonderwords.core.presentation.theme.poppins

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
                fontFamily = playWrite,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                maxLines = 14,
                overflow = TextOverflow.Clip,
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
            maxLines = 2,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.End)
        )
    }
}