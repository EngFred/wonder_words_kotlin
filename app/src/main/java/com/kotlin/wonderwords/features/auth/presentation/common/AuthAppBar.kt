package com.kotlin.wonderwords.features.auth.presentation.common

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin.wonderwords.core.presentation.theme.WonderWordsTheme
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.theme.poppinsBold

@Composable
fun AuthAppBar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    text: String,
    textSize: TextUnit = 24.sp,
    height: Dp = 66.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {

    Box(modifier = modifier.fillMaxWidth().height(height)
        .background(backgroundColor)) {
        IconButton(onClick = { onClose() }, modifier = Modifier.align(
            Alignment.CenterStart
        )) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = text,
            fontSize = textSize,
            color = Color.White,
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthAppBarPreview() {
    WonderWordsTheme(
        darkTheme = true
    ) {
        AuthAppBar(onClose = { /*TODO*/ }, text = "Login")
    }
}