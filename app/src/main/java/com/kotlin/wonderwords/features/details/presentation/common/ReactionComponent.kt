package com.kotlin.wonderwords.features.details.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kotlin.wonderwords.core.presentation.theme.SteelBlue
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.features.details.domain.models.ReactionType
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun ReactionComponent(
    modifier: Modifier = Modifier,
    type: ReactionType,
    count: Int,
    onClick: () -> Unit,
    isFavorite: Boolean = false,
    upvoted: Boolean = false,
    downVoted: Boolean = false,
    currentTheme: ThemeMode
) {

val color = if (currentTheme != ThemeMode.Light && (isSystemInDarkTheme() || currentTheme == ThemeMode.Dark)) {
    if (type == ReactionType.Upvote) {
        if(upvoted) SteelBlue else Color.LightGray
    } else if ( type == ReactionType.Dowvote ) {
        if(downVoted) SteelBlue else Color.LightGray
    } else {
        Color.LightGray
    }
} else {
    if (type == ReactionType.Upvote) {
        if(upvoted) SteelBlue else MaterialTheme.colorScheme.primary
    } else if ( type == ReactionType.Dowvote ) {
        if(downVoted) SteelBlue else MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.primary
    }
}

    val imageVector = when(type) {
        ReactionType.Favorite -> if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
        ReactionType.Upvote -> Icons.Rounded.ArrowUpward
        ReactionType.Dowvote -> Icons.Rounded.ArrowDownward
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = color
        )
        Text(
            text = "$count",
            fontSize = 13.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            color = color
        )

    }
}