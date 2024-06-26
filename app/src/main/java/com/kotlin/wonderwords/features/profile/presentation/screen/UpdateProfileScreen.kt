package com.kotlin.wonderwords.features.profile.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel

@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Update profile",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}