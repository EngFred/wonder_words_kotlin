package com.kotlin.wonderwords.features.details.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kotlin.wonderwords.features.details.domain.entity.ReactionType

@Composable
fun DetailsAppbar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onFavClick: () -> Unit,
    onUpvoteClick: () -> Unit,
    onDowvoteClick: () -> Unit,
    upvotesCount: Int,
    downvotesCount: Int,
    favoritesCount: Int,
    isFavorite: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
        }

        ReactionComponent(type = ReactionType.Favorite, count = favoritesCount, onClick = onFavClick, isFavorite =  isFavorite)
        ReactionComponent(type = ReactionType.Upvote, count = upvotesCount, onClick = onUpvoteClick)
        ReactionComponent(type = ReactionType.Dowvote, count = downvotesCount, onClick = onDowvoteClick)

        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
        }

    }
}