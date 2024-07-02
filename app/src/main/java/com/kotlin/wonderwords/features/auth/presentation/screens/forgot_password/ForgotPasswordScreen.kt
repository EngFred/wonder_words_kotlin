package com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.auth.presentation.common.CustomAppBar
import com.kotlin.wonderwords.core.presentation.common.AppButton
import com.kotlin.wonderwords.features.auth.presentation.common.AuthTextField
import com.kotlin.wonderwords.features.auth.presentation.viewModel.ForgotPasswordViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    sharedViewModel: SharedViewModel,
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    SetSystemBarColor(sharedViewModel = sharedViewModel, isAuth = true)

    val uiState = forgotPasswordViewModel.uiState.collectAsState().value
    val context = LocalContext.current

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value
    val clickableTextColor = if( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) Color.LightGray else Color.Black

    LaunchedEffect(uiState.sentResetLink) {
        if(uiState.sentResetLink){
            onLogin()
            showToast(context, "Reset link has been sent to your email", toastLength = Toast.LENGTH_LONG)
        }
    }

    LaunchedEffect(uiState.error) {
        if(uiState.error != null ){
            showToast(context, uiState.error, toastLength = Toast.LENGTH_LONG)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomAppBar(onClose = { /*TODO*/ }, text = "Forgot Password", textSize = 20.sp)
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
        AppButton(
            text = "Submit",
            enabled = !uiState.sentResetLink,
            onClick = {
                forgotPasswordViewModel.onEvent(ForgotPasswordUiEvents.SubmitButtonClicked)
            },
            isLoading = { uiState.isLoading },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        ClickableText(text = buildAnnotatedString {
            append("Remember password?")
        }, onClick = { onLogin() }, style = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.ExtraBold,
            color = clickableTextColor
        ))

    }
}