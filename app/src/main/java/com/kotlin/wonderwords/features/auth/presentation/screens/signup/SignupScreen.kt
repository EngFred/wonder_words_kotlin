package com.kotlin.wonderwords.features.auth.presentation.screens.signup

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.common.AppButton
import com.kotlin.wonderwords.core.presentation.theme.SteelBlue
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.theme.poppinsBold
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.auth.presentation.common.AuthTextField
import com.kotlin.wonderwords.features.auth.presentation.common.CustomAppBar
import com.kotlin.wonderwords.features.auth.presentation.viewModel.SignupViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    onSignupSuccess: () -> Unit,
    sharedViewModel: SharedViewModel,
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    SetSystemBarColor(sharedViewModel = sharedViewModel, isAuth = true)

    val context = LocalContext.current

    val uiState = signupViewModel.uiState.collectAsState().value

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value
    val clickableTextColor = if( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) Color.LightGray else Color.Black

    LaunchedEffect(uiState.signupError) {
        if (uiState.signupError != null) {
            showToast(context, text = uiState.signupError)
        }
    }

    LaunchedEffect(key1 = uiState.signupSuccess) {
        if (uiState.signupSuccess) {
            onSignupSuccess()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomAppBar(onClose = { /*TODO*/ }, text = "Signup")
        Spacer(modifier = Modifier.size(66.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AuthTextField(
                placeHolder = "Username",
                onTextChange = {
                    signupViewModel.onEvent(SignupUiEvents.NameChanged(it))
                },
                value = { uiState.name },
                keyboardType = KeyboardType.Text,
                imeAction = if (uiState.password.isNotBlank() && uiState.confirmedPassword.isNotBlank() && uiState.email.isNotBlank()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null)  },
                isError = {uiState.usernameError != null},
                errorMessage = { uiState.usernameError }
            )


            AuthTextField(
                placeHolder = "Email",
                onTextChange = {
                   signupViewModel.onEvent(SignupUiEvents.EmailChanged(it))
                },
                value = { uiState.email },
                keyboardType = KeyboardType.Email,
                imeAction = if (uiState.password.isNotBlank() && uiState.confirmedPassword.isNotBlank() && uiState.name.isNotBlank()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null)  },
                isError = {uiState.emailError != null},
                errorMessage = { uiState.emailError }
            )


            AuthTextField(
                placeHolder = "Password",
                onTextChange = {
                    signupViewModel.onEvent(SignupUiEvents.PasswordChanged(it))
                },
                value = { uiState.password },
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = if (uiState.name.isNotBlank() && uiState.confirmedPassword.isNotBlank() && uiState.email.isNotBlank()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Key, contentDescription = null)  },
                isError = {uiState.passwordError != null},
                errorMessage = { uiState.passwordError }
            )

            AuthTextField(
                placeHolder = "Confirm Password",
                onTextChange = {
                    signupViewModel.onEvent(SignupUiEvents.ConfirmedPasswordChanged(it))
                },
                value = { uiState.confirmedPassword },
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = if (uiState.name.isNotBlank() && uiState.password.isNotBlank() && uiState.email.isNotBlank()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Key, contentDescription = null)  },
                isError = {uiState.confirmedPassword != uiState.password},
                errorMessage = { "Password do not match" }
            )
            Spacer(modifier = Modifier.size(16.dp))
            AppButton(text = "Signup", onClick = {
                if (uiState.isFormValid) {
                    if (!uiState.isLoading) {
                        signupViewModel.onEvent(SignupUiEvents.SignupButtonClicked)
                    }
                } else {
                    showToast(context)
                }
            }, isLoading = {uiState.isLoading})
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Already registered?", fontFamily = poppins,
                fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.size(16.dp))
            ClickableText(text = buildAnnotatedString {
                append("Login")
            }, onClick = {
                if (!uiState.isLoading) {
                    onLogin()
                }
            }, style = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.ExtraBold,
                color = clickableTextColor
            ))
            Spacer(modifier = Modifier.size(16.dp))

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle( fontFamily = poppinsBold, color = SteelBlue )) {
                    append("Note:\n")
                }
                withStyle(style = SpanStyle( fontFamily = poppins, fontWeight = FontWeight.ExtraBold )) {
                    append("The ")
                }
                withStyle(style = SpanStyle( fontFamily = poppinsBold )) {
                    append("username ")
                }
                withStyle(style = SpanStyle( fontFamily = poppins, fontWeight = FontWeight.ExtraBold )) {
                    append("can only contain letters (a-z), numbers (0-9) and the underscore (_) and the max Length 20 characters!\n\n")
                }
                withStyle(style = SpanStyle( fontFamily = poppins, fontWeight = FontWeight.ExtraBold )) {
                    append("The ")
                }
                withStyle(style = SpanStyle( fontFamily = poppinsBold )) {
                    append("password ")
                }
                withStyle(style = SpanStyle( fontFamily = poppins, fontWeight = FontWeight.ExtraBold )) {
                    append("must be at least 6 characters long!")
                }
            })
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}