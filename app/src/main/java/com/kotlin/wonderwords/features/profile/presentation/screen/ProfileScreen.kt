package com.kotlin.wonderwords.features.profile.presentation.screen

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.theme.poppinsBold
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.core.utils.showToast
import com.kotlin.wonderwords.core.presentation.common.AppButton
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode
import com.kotlin.wonderwords.features.profile.presentation.common.RadioButtonTile
import com.kotlin.wonderwords.features.profile.presentation.common.UserMainInfo
import com.kotlin.wonderwords.features.profile.presentation.viewModel.ProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onUpdateProfile: (String, String) -> Unit,
    onSignOut: () -> Unit,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val uiState = profileViewModel.uiState.collectAsState().value

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value
    val username = sharedViewModel.username.collectAsState().value
    val email = sharedViewModel.userEmail.collectAsState().value

    val textColor = if( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() && currentTheme != ThemeMode.Light ) Color.LightGray else Color.Black

    val context = LocalContext.current

    SetSystemBarColor(isAuth = false, sharedViewModel = sharedViewModel)

    LaunchedEffect( uiState.signOutSuccess ) {
        if ( uiState.signOutSuccess ) {
            onSignOut()
        }
    }

    LaunchedEffect( uiState.signOutError ) {
        if ( uiState.signOutError != null ) {
            showToast(context, "Something went wrong")
        }
    }

    LaunchedEffect( uiState.error ) {
        if ( uiState.error != null ) {
            showToast(context, uiState.error)
        }
    }

    LaunchedEffect( uiState.user ) {
        if ( uiState.user != null ) {
            Log.wtf("#", "${uiState.user}")
        } else {
            Log.wtf("#", "User is null")
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            profileViewModel.reset()
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = { profileViewModel.onEvent(ProfileUiEvents.Refreshed) } // Ensure your ViewModel has this method
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            UserMainInfo(
                userProfileInfo = uiState.user,
                isLoading = uiState.isLoading,
                loadError = uiState.error,
                username = username,
                userEmail = email
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = {
                    if ( !uiState.signingOut ) {
                        if ( uiState.user != null ) {
                            onUpdateProfile(
                                username,
                                email
                            )
                        } else {
                            showToast(context, "User session not found!")
                        }
                    } },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Update Info",
                        fontSize = 20.sp,
                        color = textColor,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Theme Preferences",
                fontSize = 18.sp,
                fontFamily = poppinsBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            RadioButtonTile(
                textColor = textColor,
                selected = { currentTheme == ThemeMode.Light  },
                onClick = {
                    if (currentTheme != ThemeMode.Light && !uiState.signingOut  ) {
                        sharedViewModel.saveTheme(ThemeMode.Light)
                    }
                },
                label = "Light Theme",
            )
            RadioButtonTile(
                textColor = textColor,
                selected = { currentTheme == ThemeMode.Dark },
                onClick = {
                    if ( currentTheme != ThemeMode.Dark && !uiState.signingOut  ) {
                        sharedViewModel.saveTheme(ThemeMode.Dark)
                    }
                },
                label = "Dark Theme"
            )
            RadioButtonTile(
                textColor = textColor,
                selected = { currentTheme == ThemeMode.System },
                onClick = {
                    if ( currentTheme != ThemeMode.System && !uiState.signingOut ) {
                        sharedViewModel.saveTheme(ThemeMode.System)
                    }
                },
                label = "Use Device Theme"
            )
            Spacer(modifier = Modifier.weight(1f))
            AppButton(
                modifier = Modifier.padding(horizontal = 30.dp),
                text = "Sign out",
                onClick = {
                    if ( !uiState.signingOut ) {
                        profileViewModel.onEvent(ProfileUiEvents.LoggedOut)
                    }
                },
                icon = Icons.AutoMirrored.Rounded.Logout,
                isLoading = { uiState.signingOut  }
            )
        }
        PullRefreshIndicator(
            refreshing = uiState.isLoading,
            state = pullRefreshState,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }
}

