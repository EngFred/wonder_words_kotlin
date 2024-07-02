package com.kotlin.wonderwords.features.create_quote.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.common.AppButton
import com.kotlin.wonderwords.core.presentation.theme.DarkSlateGrey
import com.kotlin.wonderwords.core.presentation.theme.SteelBlue
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.theme.poppinsBold
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.auth.presentation.common.CustomAppBar
import com.kotlin.wonderwords.features.create_quote.presentation.viewModels.CreateQuoteViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun CreateQuoteScreen(
    modifier: Modifier = Modifier,
    username: String?,
    sharedViewModel: SharedViewModel,
    onQuotedCreatedSuccessfully: (Int) -> Unit,
    onBack: () -> Unit,
    createQuoteViewModel: CreateQuoteViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        Log.d("TAG", "$username")
    }

    val uiState = createQuoteViewModel.uiState.collectAsState().value
    val currentTheme = sharedViewModel.currentTheme.collectAsState().value

    val context = LocalContext.current

    val bgColor = if(currentTheme == ThemeMode.Dark || isSystemInDarkTheme() && currentTheme != ThemeMode.Light ) DarkSlateGrey else Color.Black

    SetSystemBarColor(isAuth = true, sharedViewModel = sharedViewModel)


    LaunchedEffect(uiState.createdQuoteId) {
        if (uiState.createdQuoteId != null) {
            onQuotedCreatedSuccessfully(uiState.createdQuoteId)
        }
    }

    LaunchedEffect(uiState.createError) {
        if (uiState.createError != null) {
            showToast(context, uiState.createError, Toast.LENGTH_LONG)
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        
        CustomAppBar(onClose = { onBack() }, text = "Create Quote" , backgroundColor = bgColor)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp, vertical = 10.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(text = "Quote body:", fontFamily = poppins, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = "Type the quote here...", fontFamily = poppins, fontWeight = FontWeight.Bold) },
                value = uiState.body,
                onValueChange = {
                    if (uiState.body.length < 200) {
                        createQuoteViewModel.onEvent(CreateQuoteUiEvents.BodyChanged(it))
                    }
                },
                isError = !uiState.bodyError.isNullOrEmpty(),
                supportingText = {
                    Text(text = uiState.bodyError ?: "", fontFamily = poppins, fontWeight = FontWeight.Bold)
                },
                maxLines = 6,
                textStyle = TextStyle(
                    fontFamily = poppins, fontWeight = FontWeight.Bold
                )
            )

            Text(text = "Quote by:", fontFamily = poppins, fontWeight = FontWeight.ExtraBold)
            TextField(
                value = uiState.author,
                onValueChange = {
                    if (uiState.author.length < 14) {
                        createQuoteViewModel.onEvent(CreateQuoteUiEvents.AuthorChanged(it))
                    }
                },
                isError = !uiState.authorError.isNullOrEmpty(),
                supportingText = {
                    Text(text = uiState.authorError ?: "", fontFamily = poppins, fontWeight = FontWeight.Bold)
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = poppins, fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Author",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            AppButton(
                modifier = Modifier.padding(horizontal = 45.dp),
                text = "Create",
                enabled = uiState.isValid,
                onClick = {
                    if (!uiState.isLoading) {
                        createQuoteViewModel.onEvent(CreateQuoteUiEvents.CreateButtonClicked)
                    }
                },
                isLoading = {
                    uiState.isLoading
                },
                icon = Icons.Rounded.Done
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle( fontFamily = poppinsBold, color = SteelBlue )) {
                    append("Note:")
                }
                withStyle(style = SpanStyle( fontFamily = poppins, fontWeight = FontWeight.ExtraBold )) {
                    append("\nCurrently there's an internal server breakdown. So you may not be able to add quote!")
                }
            })
        }

        }

    }