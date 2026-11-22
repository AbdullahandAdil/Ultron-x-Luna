
package com.ultone.assistant.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.data.Persona
import kotlinx.coroutines.launch

private val ChatGold = Color(0xFFD9AE50)
private val ChatDark = Color(0xFF080B14)
private val ChatCard = Color(0xFF111C2C)

private data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun ChatScreen(
    selectedPersona: Persona,
    userName: String
) {
    val themeColor = when (selectedPersona) {
        Persona.ULTRON -> Color(0xFF24DFFF)
        Persona.LUNA -> Color(0xFFBD65FF)
    }

    var message by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf<ChatMessage>()
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedPersona, userName) {
        messages.clear()
        messages.add(
            ChatMessage(
                text = "Hello ${userName.ifBlank { "Boss" }}! I'm ${selectedPersona.name}. How can I help you today?",
                isUser = false
            )
        )
    }

    fun sendMessage() {
        val text = message.trim()
        if (text.isNotEmpty()) {
            messages.add(
                ChatMessage(
                    text = text,
                    isUser = true
                )
            )
            message = ""

            scope.launch {
                listState.animateScrollToItem(
                    messages.lastIndex
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatDark)
    ) {

        // HEADER

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 15.dp
                )
        ) {
            Text(
                text = "${selectedPersona.name} CHAT",
                color = ChatGold,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Your personal conversation space",
                color = themeColor,
                fontSize = 12.sp
            )
        }

        // HEADER DIVIDER

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .height(1.dp)
                .background(Color(0xFF29384A))
        )

        // CHAT AREA

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            contentPadding = PaddingValues(
                top = 20.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(messages) { chatMessage ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn()
                ) {
                    ChatBubble(
                        text = chatMessage.text,
                        isUser = chatMessage.isUser,
                        themeColor = themeColor,
                        personaName = selectedPersona.name
                    )
                }
            }
        }

        // MESSAGE COMPOSER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            // MESSAGE INPUT

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(25.dp))
                    .background(ChatCard)
                    .border(
                        width = 1.dp,
                        color = themeColor.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(
                        horizontal = 17.dp,
                        vertical = 15.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    cursorBrush = androidx.compose.ui.graphics.SolidColor(
                        themeColor
                    ),
                    maxLines = 4,
                    decorationBox = { innerTextField ->
                        Box {
                            if (message.isEmpty()) {
                                Text(
                                    text = "Type your message...",
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            // SEND BUTTON

            Button(
                onClick = { sendMessage() },
                enabled = message.isNotBlank(),
                modifier = Modifier.size(54.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColor,
                    contentColor = Color.Black,
                    disabledContainerColor = themeColor.copy(
                        alpha = 0.35f
                    ),
                    disabledContentColor = Color.Black.copy(
                        alpha = 0.5f
                    )
                )
            ) {
                Text(
                    text = "➤",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// CHAT BUBBLE

@Composable
private fun ChatBubble(
    text: String,
    isUser: Boolean,
    themeColor: Color,
    personaName: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Column(
            modifier = Modifier.widthIn(max = 300.dp),
            horizontalAlignment = if (isUser) {
                Alignment.End
            } else {
                Alignment.Start
            }
        ) {

            Text(
                text = if (isUser) "YOU" else personaName,
                color = if (isUser) {
                    Color(0xFFB9C6D6)
                } else {
                    themeColor
                },
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(
                    horizontal = 7.dp,
                    vertical = 5.dp
                )
            )

            Surface(
                color = if (isUser) {
                    themeColor.copy(alpha = 0.13f)
                } else {
                    ChatCard
                },
                shape = RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp,
                    bottomStart = if (isUser) 18.dp else 5.dp,
                    bottomEnd = if (isUser) 5.dp else 18.dp
                ),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = themeColor.copy(
                        alpha = if (isUser) 0.35f else 0.18f
                    ),
                    shape = RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp,
                        bottomStart = if (isUser) 18.dp else 5.dp,
                        bottomEnd = if (isUser) 5.dp else 18.dp
                    )
                )
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}
