
package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WelcomeScreen()
        }
    }
}

@Composable
fun WelcomeScreen() {

    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF080B18),
            Color(0xFF11152B),
            Color(0xFF080B18)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "ULTRON AI ASSISTANT",
            color = Color(0xFFE4BD65),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Choose Your Assistant",
            color = Color.White,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        AssistantButton(
            name = "ULTRON",
            color = Color(0xFF00D9FF)
        )

        Spacer(modifier = Modifier.height(24.dp))

        AssistantButton(
            name = "LUNA",
            color = Color(0xFFB56BFF)
        )

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "TOGETHER, ALWAYS...",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun AssistantButton(
    name: String,
    color: Color
) {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color.copy(alpha = 0.15f)
        )
    ) {
        Text(
            text = "SELECT $name",
            color = color,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
