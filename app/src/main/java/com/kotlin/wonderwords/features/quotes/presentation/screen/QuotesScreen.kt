package com.kotlin.wonderwords.features.quotes.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.theme.DarkSlateGrey
import com.kotlin.wonderwords.core.presentation.theme.playWrite
import com.kotlin.wonderwords.core.presentation.theme.playWriteTitle
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode
import com.kotlin.wonderwords.features.quotes.domain.models.Quote
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.models.Source
import com.kotlin.wonderwords.features.quotes.presentation.components.ErrorScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.LoadingScreen
import com.kotlin.wonderwords.features.quotes.presentation.components.QuotesGrid
import com.kotlin.wonderwords.features.quotes.presentation.viewModel.QuotesViewModel

@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    onQuoteClick: (Int) -> Unit,
    onAddQuote: (String) -> Unit,
    sharedViewModel: SharedViewModel,
    quotesViewModel: QuotesViewModel = hiltViewModel()
) {

    val uiState = quotesViewModel.uiState.collectAsState().value

    val username = sharedViewModel.username.collectAsState().value
    val context = LocalContext.current

    SetSystemBarColor(isAuth = false, sharedViewModel = sharedViewModel)

    if (uiState.quoteOfTheDay != null){
        ShowQuoteOfTheDay(uiState.quoteOfTheDay, onDismiss = {
            quotesViewModel.onEvent(QuotesUiEvents.DismissedQuoteOfTheDay)
        })
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Wonder Words",
                fontFamily = playWriteTitle,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                if (username.isNotEmpty()){
                    onAddQuote(username)
                } else {
                    showToast(context, "Something went wrong!")
                }
            }) {
                Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = null)
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        CategoriesList(
            onCategorySelected = { category ->
                if (!uiState.initialLoading) {
                    quotesViewModel.onEvent(QuotesUiEvents.SelectedCategory(category))
                }
            },
            selectedCategory = { uiState.selectedCategory },
            sharedViewModel = sharedViewModel
        )

        when{
            uiState.initialLoading -> {
                LoadingScreen()
            }

            uiState.refreshError != null && !uiState.initialLoading -> {
                ErrorScreen()
            }

            else -> {
                if (uiState.quotes.isEmpty()) {
                    ErrorScreen(errorText = "No quotes found!\nEnsure you have an active internet connection and then try again.")
                } else {
                    Spacer(modifier = Modifier.height(8.dp))
                    QuotesGrid(
                        modifier = modifier,
                        quotes = uiState.quotes,
                        onQuoteClick = onQuoteClick,
                        onLoadMoreQuotes = {
                            if( uiState.source == Source.Remote ) {
                                quotesViewModel.onEvent(QuotesUiEvents.LoadedMoreQuotes)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowQuoteOfTheDay(quoteOfTheDay: Quote, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Wonder Words",
                fontWeight = FontWeight.Bold,
                fontFamily = playWriteTitle
            )
        },
        text = {
            Column {
                Text(
                    text = "“${quoteOfTheDay.body}”",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, fontFamily = playWrite,
                    lineHeight = 30.sp,
                    maxLines = 14,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "- ${quoteOfTheDay.author}",
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 14.sp,
                    maxLines = 2,
                    fontFamily = poppins
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Okay", fontFamily = poppins, fontWeight = FontWeight.Bold)
            }
        }
    )
}


@Composable
private fun CategoriesList(
    onCategorySelected: (QuoteCategory) -> Unit,
    selectedCategory: () -> QuoteCategory,
    sharedViewModel: SharedViewModel
) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value

    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        items(
            count = QuoteCategory.entries.size
        ) { index ->
            val category = QuoteCategory.entries[index]
            OutlinedButton(
                border = BorderStroke(
                    width = 1.1.dp,
                    color = if ( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) DarkSlateGrey else Color.Black
                ),
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    contentColor = if (category == selectedCategory()){
                        if ( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) {
                            Color.LightGray
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                Text(text = category.name,fontFamily = poppins, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.width(6.dp))
        }

    }
}

