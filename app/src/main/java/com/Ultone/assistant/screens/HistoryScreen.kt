
package com.ultone.assistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HistoryGold = Color(0xFFD9AE50)
private val HistoryDark = Color(0xFF080B14)
private val HistoryCard = Color(0xFF1B293D)

@Composable
fun HistoryScreen() {

    val historyItems = listOf(
        "Welcome to Ultron x Luna",
        "Your conversations will appear here",
        "Chat history is waiting for your first conversation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HistoryDark)
            .padding(18.dp)
    ) {

        Text(
            text = "CHAT HISTORY",
            color = HistoryGold,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your previous conversations",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(historyItems) { item ->

                HistoryCard(
                    title = item
                )
            }
        }
    }
}

@Composable
private fun HistoryCard(
    title: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Color(0xFF34445B),
                RoundedCornerShape(14.dp)
            )
            .background(
                HistoryCard,
                RoundedCornerShape(14.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "◷",
            color = HistoryGold,
            fontSize = 25.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
