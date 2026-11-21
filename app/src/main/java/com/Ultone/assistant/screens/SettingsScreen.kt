
package com.ultone.assistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.data.Persona

private val SettingsGold = Color(0xFFD9AE50)
private val SettingsDark = Color(0xFF080B14)
private val SettingsCard = Color(0xFF1B293D)

@Composable
fun SettingsScreen(
    selectedPersona: Persona,
    onPersonaChanged: (Persona) -> Unit
) {
    var language by remember { mutableStateOf("Auto") }
    var voiceEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SettingsDark)
            .padding(18.dp)
    ) {
        Text(
            text = "SETTINGS",
            color = SettingsGold,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        SettingsHeading("PERSONALITY")

        SettingsOption(
            title = "Active Assistant",
            description = "Choose your active personality"
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip(
                    selected = selectedPersona == Persona.ULTRON,
                    onClick = { onPersonaChanged(Persona.ULTRON) },
                    label = { Text("ULTRON") }
                )

                FilterChip(
                    selected = selectedPersona == Persona.LUNA,
                    onClick = { onPersonaChanged(Persona.LUNA) },
                    label = { Text("LUNA") }
                )
            }
        }

        SettingsHeading("VOICE")

        SettingsOption(
            title = "Voice Assistant",
            description = "Enable voice interaction while the app is open"
        ) {
            Switch(
                checked = voiceEnabled,
                onCheckedChange = { voiceEnabled = it }
            )
        }

        SettingsHeading("LANGUAGE")

        SettingsOption(
            title = "App Language",
            description = "Choose your preferred language"
        ) {
            var expanded by remember { mutableStateOf(false) }

            Box {
                OutlinedButton(
                    onClick = { expanded = true }
                ) {
                    Text(language, color = SettingsGold)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    listOf(
                        "Auto",
                        "English",
                        "Hindi",
                        "Urdu",
                        "Arabic"
                    ).forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                language = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        SettingsHeading("ABOUT")

        SettingsOption(
            title = "App Version",
            description = "Ultron x Luna • Version 1.0.0"
        )
    }
}

@Composable
private fun SettingsHeading(title: String) {
    Text(
        text = title,
        color = SettingsGold,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(
            top = 20.dp,
            bottom = 10.dp
        )
    )
}

@Composable
private fun SettingsOption(
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(
                1.dp,
                Color(0xFF34445B),
                RoundedCornerShape(14.dp)
            )
            .background(
                SettingsCard,
                RoundedCornerShape(14.dp)
            )
            .padding(15.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = description,
            color = Color.LightGray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        content()
    }
}
