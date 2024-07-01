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
import com.kotlin.wonderwords.features.details.domain.models.ReactionType
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun DetailsAppbar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onFavClick: () -> Unit,
    onUpvoteClick: () -> Unit,
    onDowvoteClick: () -> Unit,
    onShare: () -> Unit,
    upvotesCount: Int,
    downvotesCount: Int,
    favoritesCount: Int,
    favorited: Boolean,
    upvoted: Boolean,
    downVoted: Boolean,
    currentTheme: ThemeMode
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
        }

        ReactionComponent(
            type = ReactionType.Favorite,
            count = favoritesCount,
            onClick = onFavClick,
            isFavorite = favorited,
            currentTheme = currentTheme
        )
        ReactionComponent(
            type = ReactionType.Upvote,
            count = upvotesCount,
            onClick = onUpvoteClick,
            upvoted = upvoted,
            downVoted = downVoted,
            currentTheme = currentTheme
        )
        ReactionComponent(
            type = ReactionType.Dowvote,
            count = downvotesCount,
            onClick = onDowvoteClick,
            upvoted = upvoted,
            downVoted = downVoted,
            currentTheme = currentTheme
        )

        IconButton(onClick = onShare) {
            Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
        }

    }
}