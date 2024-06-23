package com.kotlin.wonderwords.features.auth.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin.wonderwords.core.presentation.theme.WonderWordsTheme

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    onTextChange: (String) -> Unit,
    value: () -> String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    leadingIcon: @Composable (() -> Unit),
    isPassword: Boolean = false,
    isError: () -> Boolean,
    errorMessage: () -> String? = { null }
) {

    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    val visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None

    TextField(
        value = value(),
        isError = isError(),
        supportingText = {
            if (isError()) {
                Text(text = errorMessage() ?: "")
            }
        },
        onValueChange = onTextChange,
        leadingIcon = { leadingIcon() },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        trailingIcon = {
             if (isPassword) {
                 IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                     Icon(imageVector = if (passwordVisibility) {
                         Icons.Rounded.Visibility
                     } else {
                         Icons.Rounded.VisibilityOff
                     }, contentDescription = null)
                 }
             }
        },
        maxLines = 1,
        placeholder = { Text(text = placeHolder) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    WonderWordsTheme {
        AuthTextField(
            placeHolder = "Email",
            onTextChange = {},
            value = { "" },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) },
            isError = {false}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview2() {
    WonderWordsTheme {
        AuthTextField(
            placeHolder = "Password",
            onTextChange = {},
            value = { "" },
            isPassword = true,
            leadingIcon = { Icon(imageVector = Icons.Rounded.Key, contentDescription = null) },
            isError = {false}
        )
    }
}