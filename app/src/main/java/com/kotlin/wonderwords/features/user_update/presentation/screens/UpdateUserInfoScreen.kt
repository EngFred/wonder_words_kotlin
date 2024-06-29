package com.kotlin.wonderwords.features.user_update.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.features.auth.presentation.common.CustomAppBar
import com.kotlin.wonderwords.features.auth.presentation.common.AuthButton
import com.kotlin.wonderwords.features.auth.presentation.common.AuthTextField
import com.kotlin.wonderwords.features.user_update.presentation.viewModel.UpdateUserViewModel

@Composable
fun UpdateUserScreen(
    modifier: Modifier = Modifier,
    onUpdateSuccess: () -> Unit,
    sharedViewModel: SharedViewModel,
    username: String,
    email: String,
    updateUserViewModel: UpdateUserViewModel = hiltViewModel()
) {
    SetSystemBarColor(sharedViewModel = sharedViewModel, isAuth = true)

    val context = LocalContext.current

    val uiState = updateUserViewModel.uiState.collectAsState().value

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value

    LaunchedEffect(uiState.updateError) {
        if (uiState.updateError != null) {
            showToast(context, text = "Something went wrong!")
        }
    }

    LaunchedEffect(key1 = uiState.updateSuccess) {
        if (uiState.updateSuccess) {
            onUpdateSuccess()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomAppBar(onClose = {
               if ( !uiState.isUpdating )    {
                   onUpdateSuccess()
               }
        }, text = "Update Info")
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AuthTextField(
                placeHolder = "Username",
                onTextChange = {
                    updateUserViewModel.onEvent(UpdateUserUiEvents.UsernameChanged(it))
                },
                value = { uiState.username ?: "" },
                keyboardType = KeyboardType.Text,
                imeAction = if (!uiState.password.isNullOrEmpty() && !uiState.email.isNullOrEmpty() && !uiState.password.isNullOrEmpty() && !uiState.twitterUsername.isNullOrEmpty() && !uiState.facebookUsername.isNullOrEmpty()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null)  },
                isError = {uiState.usernameError != null},
                errorMessage = { uiState.usernameError }
            )


            AuthTextField(
                placeHolder = "Email",
                onTextChange = {
                    updateUserViewModel.onEvent(UpdateUserUiEvents.EmailChanged(it))
                },
                value = { uiState.email ?: "" },
                keyboardType = KeyboardType.Email,
                imeAction = if (!uiState.password.isNullOrEmpty() && !uiState.username.isNullOrEmpty() && !uiState.password.isNullOrEmpty() && !uiState.twitterUsername.isNullOrEmpty() && !uiState.facebookUsername.isNullOrEmpty()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null)  },
                isError = {uiState.emailError != null},
                errorMessage = { uiState.emailError }
            )


            AuthTextField(
                placeHolder = "Password",
                onTextChange = {
                    updateUserViewModel.onEvent(UpdateUserUiEvents.PasswordChanged(it))
                },
                value = { uiState.password ?: "" },
                isPassword = false,
                keyboardType = KeyboardType.Password,
                imeAction = if (!uiState.password.isNullOrEmpty() && !uiState.email.isNullOrEmpty() && !uiState.username.isNullOrEmpty() && !uiState.twitterUsername.isNullOrEmpty() && !uiState.facebookUsername.isNullOrEmpty()) ImeAction.Done else ImeAction.Next,
                leadingIcon = { Icon(imageVector = Icons.Rounded.Key, contentDescription = null)  },
                isError = {uiState.passwordError != null},
                errorMessage = { uiState.passwordError }
            )

//            AuthTextField(
//                placeHolder = "X username",
//                onTextChange = {
//                    updateUserViewModel.onEvent(UpdateUserUiEvents.TwitterUsernameChanged(it))
//                },
//                value = { uiState.twitterUsername ?: "" },
//                isPassword = false,
//                keyboardType = KeyboardType.Text,
//                imeAction = if (!uiState.password.isNullOrEmpty() && !uiState.email.isNullOrEmpty() && !uiState.password.isNullOrEmpty() && !uiState.username.isNullOrEmpty() && !uiState.facebookUsername.isNullOrEmpty()) ImeAction.Done else ImeAction.Next,
//                leadingIcon = { Icon(imageVector = Icons.Rounded.ConnectWithoutContact, contentDescription = null)  },
//            )
//
//            AuthTextField(
//                placeHolder = "Facebook username",
//                onTextChange = {
//                    updateUserViewModel.onEvent(UpdateUserUiEvents.FacebookUsernameChanged(it))
//                },
//                value = { uiState.facebookUsername ?: "" },
//                isPassword = false,
//                keyboardType = KeyboardType.Text,
//                imeAction = if (!uiState.password.isNullOrEmpty() && !uiState.email.isNullOrEmpty() && !uiState.password.isNullOrEmpty() && !uiState.username.isNullOrEmpty() && !uiState.username.isNullOrEmpty()) ImeAction.Done else ImeAction.Next,
//                leadingIcon = { Icon(imageVector = Icons.Rounded.Facebook, contentDescription = null)  },
//            )
//
//            Spacer(modifier = Modifier.size(16.dp))
//
//            Box(
//                contentAlignment = Alignment.TopStart,
//                modifier = Modifier.align(Alignment.Start)
//            ) {
//                Button(
//                    modifier = Modifier.width(220.dp).height(45.dp),
//                    shape = RoundedCornerShape(7.dp),
//                    onClick = {
//                    updateUserViewModel.onEvent(UpdateUserUiEvents.SelectSourceButtonClicked)
//                }) {
//                    val text = if ( uiState.profilePicSource?.name == null || uiState.profilePicSource == ProfilePicSource.None ) "Select pic source" else uiState.profilePicSource.name
//                    Text(text = text, color = Color.LightGray)
//                }
//
//                DropdownMenu(
//                    expanded = uiState.expanded,
//                    onDismissRequest = {
//                        updateUserViewModel.onEvent(UpdateUserUiEvents.SelectSourceButtonClicked)
//                    }
//                ) {
//                    ProfilePicSource.entries.forEach { source ->
//                        DropdownMenuItem(
//                            text = { Text(text = source.name) },
//                            onClick = {
//                                updateUserViewModel.onEvent(UpdateUserUiEvents.ProfilePicSourceChanged(source))
//                            }
//                        )
//                        HorizontalDivider()
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.weight(1f))

            AuthButton(
                icon = Icons.Rounded.Update,
                text = "Update",
                enabled = uiState.usernameError.isNullOrEmpty() && uiState.emailError.isNullOrEmpty() && uiState.passwordError.isNullOrEmpty(),
                onClick = {
                    updateUserViewModel.onEvent(UpdateUserUiEvents.UpdateButtonClicked)
            }, isLoading = {uiState.isUpdating})
        }
    }
}