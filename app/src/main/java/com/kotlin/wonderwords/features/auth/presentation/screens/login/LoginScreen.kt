package com.kotlin.wonderwords.features.auth.presentation.screens.login

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.auth.presentation.common.CustomAppBar
import com.kotlin.wonderwords.features.auth.presentation.common.AuthButton
import com.kotlin.wonderwords.features.auth.presentation.common.AuthTextField
import com.kotlin.wonderwords.features.auth.presentation.viewModel.LoginViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignup: () -> Unit,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    sharedViewModel: SharedViewModel,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    
    SetSystemBarColor(sharedViewModel = sharedViewModel, isAuth = true)
    val context = LocalContext.current


    val uiState = loginViewModel.uiState.collectAsState().value

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value
    val clickableTextColor = if( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) Color.LightGray else Color.Black

    LaunchedEffect(key1 = uiState.loginError, key2 = uiState.isLoading ) {
        if (uiState.loginError != null && !uiState.isLoading) {
            showToast(context, text = "Something went wrong!")
        }
    }

    LaunchedEffect(key1 = uiState.logInSuccess) {
        if (uiState.logInSuccess) {
            onLogin()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomAppBar(onClose = { /*TODO*/ }, text = "Login")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AuthTextField(
                placeHolder = "Username or email",
                onTextChange = {
                      loginViewModel.onEvent(LoginUiEvents.LoginChanged(it))
                },
                value = { uiState.login },
                keyboardType = KeyboardType.Email,
                imeAction = if (uiState.password.isNotBlank()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null)  },
                isError = { false }
            )

            AuthTextField(
                placeHolder = "Password",
                onTextChange = {
                    loginViewModel.onEvent(LoginUiEvents.PasswordChanged(it))
                },
                value = { uiState.password },
                isPassword = true,
                keyboardType = KeyboardType.Password,
                imeAction = if (uiState.login.isNotBlank()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Key, contentDescription = null)  },
                isError = { false }
            )
            Spacer(modifier = Modifier.size(16.dp))
            ClickableText(text = buildAnnotatedString {
                append("Forgot password?")
            }, onClick = {
                if (!uiState.isLoading) {
                    onForgotPassword()
                }
            }, style = TextStyle(
                color = clickableTextColor,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            ))
            Spacer(modifier = Modifier.size(16.dp))
            AuthButton(text = "Login", onClick = {
                if (uiState.isFormValid) {
                    if (!uiState.isLoading) {
                        loginViewModel.onEvent(LoginUiEvents.LoginButtonClicked)
                    }
                } else {
                    showToast(context)
                }
            }, isLoading = {uiState.isLoading})
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Don't have an account?",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(16.dp))
            ClickableText(text = buildAnnotatedString {
                append("Signup")
            }, onClick = {
                if (!uiState.isLoading) {
                    onSignup()
                }
            }, style = TextStyle(
                color = clickableTextColor,
                fontFamily = poppins,
                fontWeight = FontWeight.ExtraBold
            ))
        }
    }

}