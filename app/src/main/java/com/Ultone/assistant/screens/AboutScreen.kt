
package com.ultone.assistant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.R

private val AboutGold = Color(0xFFD9AE50)
private val AboutDark = Color(0xFF080B14)

@Composable
fun AboutScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AboutDark)
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "Ultron x Luna Logo",
            modifier = Modifier.size(145.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "ULTRON X LUNA",
            color = AboutGold,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "YOUR PERSONAL AI ASSISTANT",
            color = Color.LightGray,
            fontSize = 12.sp,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "One Assistant. Two Personalities.",
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "ULTRON is professional, intelligent and powerful. LUNA is caring, intelligent and friendly. Both share one common memory and help you in your daily tasks.",
            color = Color.LightGray,
            fontSize = 14.sp,
            lineHeight = 23.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        AboutInfoRow(
            title = "App Version",
            value = "1.0.0"
        )

        AboutInfoRow(
            title = "Platform",
            value = "Android"
        )

        AboutInfoRow(
            title = "AI Personalities",
            value = "ULTRON & LUNA"
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "TOGETHER, ALWAYS...",
            color = AboutGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun AboutInfoRow(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            color = Color.Gray,
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
