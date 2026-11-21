package com.ultone.assistant.voice

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.ultone.assistant.data.Persona

class VoiceAssistantController(
    context: Context,
    persona: Persona,
    private val onCommand: (String) -> Unit,
    private val onStatus: (String) -> Unit,
    private val onWakeWord: (String) -> Unit
) {

    private val handler = Handler(Looper.getMainLooper())

    private var isRunning = false
    private var isDestroyed = false

    private val voiceAssistant = VoiceAssistant(
        onWakeWordDetected = { name ->
            onWakeWord(name)
        },
        onCommandDetected = { command ->
            onCommand(command)
        },
        onStatusChanged = { status ->
            onStatus(status)
        }
    )

    private val speechController = SpeechController(
        context = context,
        onResult = { result ->
            if (isRunning) {
                voiceAssistant.processSpeech(result)
                restartListening()
            }
        },
        onError = { error ->
            if (isRunning) {
                onStatus(error)
                restartListening()
            }
        }
    )

    init {
        voiceAssistant.setActivePersona(persona.name)
        speechController.initialize()
    }

    fun start(language: String = "en-US") {
        if (isDestroyed || isRunning) return

        isRunning = true
        currentLanguage = language
        onStatus("Listening for ${activePersonaName()}")

        speechController.startListening(language)
    }

    private var currentLanguage = "en-US"

    private fun restartListening() {
        if (!isRunning || isDestroyed) return

        handler.postDelayed({
            if (isRunning && !isDestroyed) {
                speechController.startListening(currentLanguage)
            }
        }, 700)
    }

    fun stop() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
        speechController.stopListening()
        voiceAssistant.deactivate()
    }

    fun destroy() {
        stop()
        isDestroyed = true
        speechController.destroy()
    }

    private fun activePersonaName(): String {
        return if (voiceAssistant.isActive()) "active" else "persona"
    }
}