package com.ultone.assistant.voice

import java.util.Locale

class VoiceAssistant(
    private val onWakeWordDetected: (String) -> Unit,
    private val onCommandDetected: (String) -> Unit,
    private val onStatusChanged: (String) -> Unit
) {

    private var activePersona = "ULTRON"
    private var isActivated = false

    fun setActivePersona(persona: String) {
        activePersona = persona.uppercase(Locale.ROOT)
        isActivated = false
        onStatusChanged("Say $activePersona to activate")
    }

    fun processSpeech(spokenText: String) {
        val text = spokenText.trim().lowercase(Locale.ROOT)

        if (text.isBlank()) return

        val wakeWord = activePersona.lowercase(Locale.ROOT)

        if (!isActivated) {
            if (containsWakeWord(text, wakeWord)) {
                isActivated = true
                onWakeWordDetected(activePersona)
                onStatusChanged("$activePersona activated. I'm listening.")
            }
            return
        }

        if (containsWakeWord(text, wakeWord)) {
            onStatusChanged("$activePersona is already active.")
            return
        }

        if (text in listOf("stop", "cancel", "sleep", "go to sleep")) {
            isActivated = false
            onStatusChanged("$activePersona deactivated.")
            return
        }

        onCommandDetected(text)
        onStatusChanged("Command received.")
    }

    fun deactivate() {
        isActivated = false
        onStatusChanged("Say $activePersona to activate")
    }

    fun isActive(): Boolean = isActivated

    private fun containsWakeWord(text: String, wakeWord: String): Boolean {
        return text.split(Regex("[^a-zA-Z0-9]+"))
            .any { it == wakeWord }
    }
}