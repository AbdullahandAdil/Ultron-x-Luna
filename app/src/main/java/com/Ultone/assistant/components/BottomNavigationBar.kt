
package com.ultone.assistant.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.ultone.assistant.AppPage

private val NavGold = Color(0xFFD9AE50)
private val NavBackground = Color(0xFF111C2C)

@Composable
fun BottomNavigationBar(
    selectedPage: AppPage,
    onPageSelected: (AppPage) -> Unit
) {
    val items = listOf(
        Triple(AppPage.HOME, "⌂", "Home"),
        Triple(AppPage.CHAT, "▤", "Chat"),
        Triple(AppPage.AGENTS, "✧", "Agents"),
        Triple(AppPage.HISTORY, "◷", "History"),
        Triple(AppPage.ABOUT, "ⓘ", "About")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavBackground)
            .padding(
                horizontal = 5.dp,
                vertical = 9.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->

            val selected = selectedPage == item.first

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onPageSelected(item.first)
                    }
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.second,
                    color = if (selected) NavGold else Color.Gray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = item.third,
                    color = if (selected) NavGold else Color.LightGray,
                    fontSize = 9.sp,
                    fontWeight = if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                )

                if (selected) {
                    Spacer(modifier = Modifier.height(3.dp))

                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(2.dp)
                            .background(
                                NavGold,
                                RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        }
    }
}
