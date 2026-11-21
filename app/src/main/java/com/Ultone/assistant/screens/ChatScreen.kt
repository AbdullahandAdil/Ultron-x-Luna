
package com.ultone.assistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.Persona

private val ChatGold = Color(0xFFD9AE50)
private val ChatDark = Color(0xFF080B14)
private val ChatCard = Color(0xFF1B293D)

@Composable
fun ChatScreen(
    selectedPersona: Persona,
    userName: String
) {
    var message by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatDark)
            .padding(16.dp)
    ) {

        Text(
            text = "${selectedPersona.name} CHAT",
            color = ChatGold,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your personal conversation space",
            color = Color.LightGray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                ChatBubble(
                    text = "Hello $userName! I'm ${selectedPersona.name}. How can I help you?",
                    isUser = false
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type your message...")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = ChatGold,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = ChatGold
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    // AI message sending will be connected later.
                    message = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ChatGold,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
            ) {
                Text("SEND")
            }
        }
    }
}

@Composable
private fun ChatBubble(
    text: String,
    isUser: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Surface(
            color = if (isUser) {
                Color(0xFF34445B)
            } else {
                ChatCard
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.widthIn(max = 290.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(14.dp)
            )
        }
    }
}
