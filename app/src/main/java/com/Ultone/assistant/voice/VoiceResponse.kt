package com.ultone.assistant.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class VoiceResponse(
    context: Context,
    private val onReady: (Boolean) -> Unit = {}
) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null
    private var isReady = false

    init {
        textToSpeech = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        isReady = status == TextToSpeech.SUCCESS
        onReady(isReady)
    }

    fun speak(
        text: String,
        persona: String = "ULTRON",
        language: String = "en"
    ) {
        if (!isReady || text.isBlank()) return

        val locale = when (language.lowercase(Locale.ROOT)) {
            "hi", "hindi" -> Locale("hi", "IN")
            "en", "english" -> Locale.US
            else -> Locale.getDefault()
        }

        val result = textToSpeech?.setLanguage(locale)

        if (result == TextToSpeech.LANG_MISSING_DATA ||
            result == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            textToSpeech?.setLanguage(Locale.US)
        }

        when (persona.uppercase(Locale.ROOT)) {
            "ULTRON" -> {
                textToSpeech?.setPitch(0.75f)
                textToSpeech?.setSpeechRate(0.90f)
            }

            "LUNA" -> {
                textToSpeech?.setPitch(1.15f)
                textToSpeech?.setSpeechRate(1.0f)
            }

            else -> {
                textToSpeech?.setPitch(1.0f)
                textToSpeech?.setSpeechRate(1.0f)
            }
        }

        textToSpeech?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "ultron_luna_response"
        )
    }

    fun stop() {
        textToSpeech?.stop()
    }

    fun release() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isReady = false
    }
        }