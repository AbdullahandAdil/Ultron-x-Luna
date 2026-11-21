
package com.ultone.assistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AgentGold = Color(0xFFD9AE50)
private val AgentDark = Color(0xFF080B14)
private val AgentCard = Color(0xFF1B293D)

@Composable
fun AgentsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AgentDark)
            .padding(18.dp)
    ) {

        Text(
            text = "AI AGENTS",
            color = AgentGold,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your intelligent task assistants",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        AgentCard(
            title = "Research Agent",
            description = "Research topics and organize useful information.",
            icon = "🔍"
        )

        AgentCard(
            title = "Writing Agent",
            description = "Help with writing, messages and documents.",
            icon = "✍️"
        )

        AgentCard(
            title = "Productivity Agent",
            description = "Help organize tasks and daily activities.",
            icon = "⚡"
        )

        AgentCard(
            title = "Creative Agent",
            description = "Generate ideas and creative concepts.",
            icon = "💡"
        )
    }
}

@Composable
private fun AgentCard(
    title: String,
    description: String,
    icon: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .border(
                1.dp,
                Color(0xFF34445B),
                RoundedCornerShape(16.dp)
            )
            .background(
                AgentCard,
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                color = AgentGold,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "COMING SOON",
            color = Color(0xFF8B9BB2),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}
