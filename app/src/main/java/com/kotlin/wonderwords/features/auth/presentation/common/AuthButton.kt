package com.kotlin.wonderwords.features.auth.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kotlin.wonderwords.core.presentation.theme.WonderWordsTheme
import com.kotlin.wonderwords.core.presentation.theme.poppins

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    cornerSize: Dp = 8.dp,
    height: Dp = 75.dp,
    icon: ImageVector = Icons.AutoMirrored.Rounded.Login,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    isLoading: () -> Boolean,
    enabled: Boolean = true
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(cornerSize)
    ) {

        if (!isLoading()) {
            Row {
                Icon(imageVector = icon , contentDescription = null, tint = Color.LightGray )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text, color = Color.LightGray, fontFamily = poppins, fontWeight = FontWeight.ExtraBold )
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.size(40.dp), color = Color.LightGray)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AuthButtonPreview() {
    WonderWordsTheme(
        darkTheme = false
    ) {
        AuthButton(text = "SignIn", onClick = {  }, isLoading = {false})
    }
}