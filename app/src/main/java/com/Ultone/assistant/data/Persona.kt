package com.ultone.assistant.data

enum class Persona(
    val displayName: String,
    val subtitle: String,
    val voiceStyle: String
) {
    ULTRON(
        displayName = "ULTRON",
        subtitle = "Professional • Intelligent • Powerful",
        voiceStyle = "Deep"
    ),

    LUNA(
        displayName = "LUNA",
        subtitle = "Caring • Intelligent • Friendly",
        voiceStyle = "Soft"
    )
}