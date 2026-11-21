
package com.ultone.assistant.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val GreetingGold = Color(0xFFD9AE50)

@Composable
fun GreetingPreview(
    userName: String,
    centered: Boolean = true
) {
    val hour = java.time.LocalTime.now().hour

    val greeting = when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    Column(
        horizontalAlignment = if (centered) {
            Alignment.CenterHorizontally
        } else {
            Alignment.Start
        }
    ) {
        Text(
            text = "$greeting, ${userName.ifBlank { "Boss" }}!",
            color = GreetingGold,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Together, let's make today amazing.",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}
