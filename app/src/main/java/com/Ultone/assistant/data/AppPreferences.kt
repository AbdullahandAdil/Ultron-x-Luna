
package com.ultone.assistant.data

import android.content.Context

object AppPreferences {

    private const val PREF_NAME = "ultron_luna_preferences"

    private const val KEY_USER_NAME = "user_name"
    private const val KEY_PERSONA = "selected_persona"
    private const val KEY_SETUP_COMPLETE = "setup_complete"

    private fun preferences(context: Context) =
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

    fun saveUserName(context: Context, name: String) {
        preferences(context)
            .edit()
            .putString(KEY_USER_NAME, name)
            .apply()
    }

    fun getUserName(context: Context): String {
        return preferences(context)
            .getString(KEY_USER_NAME, "Boss")
            ?: "Boss"
    }

    fun savePersona(context: Context, persona: Persona) {
        preferences(context)
            .edit()
            .putString(KEY_PERSONA, persona.name)
            .apply()
    }

    fun getPersona(context: Context): Persona {
        val savedPersona = preferences(context)
            .getString(KEY_PERSONA, Persona.ULTRON.name)

        return runCatching {
            Persona.valueOf(savedPersona ?: "ULTRON")
        }.getOrDefault(Persona.ULTRON)
    }

    fun setSetupComplete(context: Context, complete: Boolean) {
        preferences(context)
            .edit()
            .putBoolean(KEY_SETUP_COMPLETE, complete)
            .apply()
    }

    fun isSetupComplete(context: Context): Boolean {
        return preferences(context)
            .getBoolean(KEY_SETUP_COMPLETE, false)
    }

    fun clearPreferences(context: Context) {
        preferences(context)
            .edit()
            .clear()
            .apply()
    }
}
