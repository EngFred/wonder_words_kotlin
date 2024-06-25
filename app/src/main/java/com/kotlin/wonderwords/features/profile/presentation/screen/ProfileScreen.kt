package com.kotlin.wonderwords.features.profile.presentation.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.features.auth.domain.entity.User
import com.kotlin.wonderwords.features.auth.presentation.common.AuthButton
import com.kotlin.wonderwords.features.profile.domain.entity.ThemeMode
import com.kotlin.wonderwords.features.profile.presentation.common.RadioButtonTile
import com.kotlin.wonderwords.features.profile.presentation.common.UserMainInfo
import com.kotlin.wonderwords.features.profile.presentation.viewModel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onUpdateProfile: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val uiState = profileViewModel.uiState.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        UserMainInfo(user = User())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onUpdateProfile,
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
                Text(text = "Update info", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos, contentDescription = null )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Theme Preferences",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        RadioButtonTile(
            selected = { uiState.selectTheme == ThemeMode.Light },
            onClick = { profileViewModel.onEvent(ProfileUiEvents.ThemeChange(ThemeMode.Light)) },
            label = "Light Theme"
        )
        RadioButtonTile(
            selected = { uiState.selectTheme == ThemeMode.Dark },
            onClick = { profileViewModel.onEvent(ProfileUiEvents.ThemeChange(ThemeMode.Dark)) },
            label = "Dark Theme"
        )
        RadioButtonTile(
            selected = { uiState.selectTheme == ThemeMode.System },
            onClick = { profileViewModel.onEvent(ProfileUiEvents.ThemeChange(ThemeMode.System)) },
            label = "Use system settings"
        )
        Spacer(modifier = Modifier.weight(1f))
        AuthButton(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = "Sign out",
            onClick = { /*TODO*/ },
            icon = Icons.AutoMirrored.Rounded.Logout,
            isLoading = { false }
        )
    }
}

