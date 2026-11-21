package com.ultone.assistant.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ultone.assistant.data.Persona

object PersonaTheme {

    fun primary(persona: Persona): Color {
        return when (persona) {
            Persona.ULTRON -> AppColors.UltronPrimary
            Persona.LUNA -> AppColors.LunaPrimary
        }
    }

    fun secondary(persona: Persona): Color {
        return when (persona) {
            Persona.ULTRON -> AppColors.UltronSecondary
            Persona.LUNA -> AppColors.LunaSecondary
        }
    }

    fun glow(persona: Persona): Color {
        return when (persona) {
            Persona.ULTRON -> AppColors.UltronGlow
            Persona.LUNA -> AppColors.LunaGlow
        }
    }
}

@Composable
fun personaAccent(persona: Persona): Color {
    return PersonaTheme.primary(persona)
}