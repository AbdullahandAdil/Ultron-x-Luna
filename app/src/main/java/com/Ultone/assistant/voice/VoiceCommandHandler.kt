package com.ultone.assistant.voice

import com.ultone.assistant.AppPage
import java.util.Locale

class VoiceCommandHandler(
    private val onNavigate: (AppPage) -> Unit,
    private val onResponse: (String) -> Unit
) {

    fun handleCommand(command: String) {
        val text = command.trim().lowercase(Locale.ROOT)

        when {
            text.contains("open home") ||
            text == "home" -> {
                onNavigate(AppPage.HOME)
                onResponse("Opening Home")
            }

            text.contains("open chat") ||
            text == "chat" -> {
                onNavigate(AppPage.CHAT)
                onResponse("Opening Chat")
            }

            text.contains("open agents") ||
            text == "agents" -> {
                onNavigate(AppPage.AGENTS)
                onResponse("Opening Agents")
            }

            text.contains("open history") ||
            text == "history" -> {
                onNavigate(AppPage.HISTORY)
                onResponse("Opening History")
            }

            text.contains("open about") ||
            text == "about" -> {
                onNavigate(AppPage.ABOUT)
                onResponse("Opening About")
            }

            text.contains("open settings") ||
            text == "settings" -> {
                onNavigate(AppPage.SETTINGS)
                onResponse("Opening Settings")
            }

            text == "go back" ||
            text == "back" -> {
                onNavigate(AppPage.HOME)
                onResponse("Going back")
            }

            else -> {
                onResponse("I heard: $command")
            }
        }
    }
}