package com.kotlin.wonderwords.features.auth.presentation.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.auth.presentation.common.AuthAppBar
import com.kotlin.wonderwords.features.auth.presentation.common.AuthButton
import com.kotlin.wonderwords.features.auth.presentation.common.AuthTextField
import com.kotlin.wonderwords.features.auth.presentation.viewModel.SignupViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    onSignupSuccess: () -> Unit,
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    SetSystemBarColor(barColor = Color.Black)

    val context = LocalContext.current

    val uiState = signupViewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = uiState.signupError, key2 = uiState.isLoading ) {
        if (uiState.signupError != null && !uiState.isLoading) {
            showToast(context, text = "Something went wrong!")
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

        AuthAppBar(onClose = { /*TODO*/ }, text = "Signup")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
            AuthButton(text = "Signup", onClick = {
                if (uiState.isFormValid) {
                    if (!uiState.isLoading) {
                        signupViewModel.onEvent(SignupUiEvents.SignupButtonClicked)
                    }
                } else {
                    showToast(context)
                }
            }, isLoading = {uiState.isLoading})
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Already registered?")
            Spacer(modifier = Modifier.size(16.dp))
            ClickableText(text = buildAnnotatedString {
                append("Login")
            }, onClick = {
                if (!uiState.isLoading) {
                    onLogin()
                }
            })
        }
    }
}