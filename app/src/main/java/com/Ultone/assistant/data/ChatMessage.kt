
package com.ultone.assistant.data

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val persona: String = "ULTRON"
)
