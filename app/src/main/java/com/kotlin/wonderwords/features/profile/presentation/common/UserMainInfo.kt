package com.kotlin.wonderwords.features.profile.presentation.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.imageLoader
import com.kotlin.wonderwords.core.presentation.theme.poppins
import com.kotlin.wonderwords.core.presentation.theme.poppinsBold
import com.kotlin.wonderwords.features.profile.domain.model.UserProfileDetails

@Composable
fun UserMainInfo(
    modifier: Modifier = Modifier,
    userProfileInfo: UserProfileDetails?,
    isLoading: Boolean = true,
    loadError: String?,
    username: String,
    userEmail: String
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
        ){
            AsyncImage(
                model = "https://cdn.motor1.com/images/mgl/9mN1A0/306:1918:3672:2754/2024-land-rover-range-rover-sv-carmel-edition.webp",
                contentDescription = null,
                imageLoader = context.imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .height(190.dp)
                    .background(Color.LightGray)
            )

            AsyncImage(
                model = userProfileInfo?.picUrl,
                contentDescription = null,
                imageLoader = context.imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.BottomCenter)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = username.replaceFirstChar { it.uppercase() },
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = poppinsBold,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
        Text(
            text = userEmail,
            fontFamily = poppins,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 5.dp)
        ) {
            if( !isLoading && loadError == null && userProfileInfo != null) {

                val followers = userProfileInfo.followers ?: 0
                val following = userProfileInfo.following ?: 0

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold, fontFamily = poppinsBold)
                    ) {
                        append(following.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = poppins, fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append(" Following")
                    }
                })

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold, fontFamily = poppinsBold)
                    ) {
                        append(followers.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = poppins, fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append(" Following")
                    }
                })
                Log.wtf("#", "Following: ${userProfileInfo.following}\nFollowers: ${userProfileInfo.followers}")

            } else {
                Text("-")
                Text(text = "-")
            }
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//private fun UserMainInfoPreview() {
//    WonderWordsTheme {
//        UserMainInfo(user = UserProfileDetails())
//    }
//}