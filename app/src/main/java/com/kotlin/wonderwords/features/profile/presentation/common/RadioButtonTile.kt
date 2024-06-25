package com.kotlin.wonderwords.features.profile.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonTile(
    modifier: Modifier = Modifier,
    selected: () -> Boolean,
    onClick: () -> Unit,
    label: String
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected(), onClick = onClick)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = label)
        }
    }

    HorizontalDivider()
}