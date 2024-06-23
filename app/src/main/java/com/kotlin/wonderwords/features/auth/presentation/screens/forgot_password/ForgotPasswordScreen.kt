package com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.features.auth.presentation.common.AuthAppBar
import com.kotlin.wonderwords.features.auth.presentation.common.AuthButton
import com.kotlin.wonderwords.features.auth.presentation.common.AuthTextField
import com.kotlin.wonderwords.features.auth.presentation.viewModel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    SetSystemBarColor(barColor = Color.Black)

    val uiState = forgotPasswordViewModel.uiState.collectAsState().value

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthAppBar(onClose = { /*TODO*/ }, text = "Forgot Password", textSize = 20.sp)
        Spacer(modifier = Modifier.size(55.dp))
        AuthTextField(
            modifier = Modifier.padding(horizontal = 16.dp),
            placeHolder = "Email",
            onTextChange = {
                forgotPasswordViewModel.onEvent(ForgotPasswordUiEvents.EmailChanged(it))
            },
            value = { uiState.email },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null)  },
            isError = {uiState.emailError != null},
            errorMessage = { uiState.emailError }
        )

        Spacer(modifier = Modifier.size(16.dp))
        AuthButton(text = "Submit", onClick = { }, isLoading = { uiState.isLoading }, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.size(16.dp))
        ClickableText(text = buildAnnotatedString {
            append("Remember password?")
        }, onClick = { onLogin() })

    }
}