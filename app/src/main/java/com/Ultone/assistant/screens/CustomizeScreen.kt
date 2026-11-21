
package com.ultone.assistant.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.Persona
import com.ultone.assistant.R

private val Gold = Color(0xFFD9AE50)
private val Dark = Color(0xFF101827)
private val CardDark = Color(0xFF1B293D)

@Composable
fun CustomizeScreen(
    selectedPersona: Persona,
    userName: String,
    onPersonaSelected: (Persona) -> Unit,
    onNameChanged: (String) -> Unit,
    onSave: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val greeting = when (
        java.time.LocalTime.now().hour
    ) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("☰", color = Color.White, fontSize = 27.sp)

            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(54.dp)
            )

            androidx.compose.material3.IconButton(
                onClick = onSettingsClick
            ) {
                Text("⚙", color = Color.White, fontSize = 25.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "PERSONAL AI ASSISTANT",
            color = Gold,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Choose Your Assistant",
            color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Select the personality you want to talk to",
            color = Color.LightGray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PersonaChoiceCard(
                modifier = Modifier.weight(1f),
                persona = Persona.ULTRON,
                selected = selectedPersona == Persona.ULTRON,
                imageId = R.drawable.ultron_character,
                subtitle = "Professional • Intelligent • Powerful",
                onClick = {
                    onPersonaSelected(Persona.ULTRON)
                }
            )

            PersonaChoiceCard(
                modifier = Modifier.weight(1f),
                persona = Persona.LUNA,
                selected = selectedPersona == Persona.LUNA,
                imageId = R.drawable.luna_character,
                subtitle = "Caring • Intelligent • Friendly",
                onClick = {
                    onPersonaSelected(Persona.LUNA)
                }
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "What should I call you?",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            value = userName,
            onValueChange = onNameChanged,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 17.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    CardDark,
                    RoundedCornerShape(12.dp)
                )
                .border(
                    1.dp,
                    Gold,
                    RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            decorationBox = { innerTextField ->
                Box {
                    if (userName.isEmpty()) {
                        Text(
                            "Enter your name",
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "$greeting, ${userName.ifBlank { "Boss" }}!",
            color = Gold,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gold,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                "SAVE & CONTINUE",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "TOGETHER, ALWAYS...",
            color = Gold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Text(
            text = "One Assistant. Two Personalities. Endless Possibilities.",
            color = Color.LightGray,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun PersonaChoiceCard(
    modifier: Modifier = Modifier,
    persona: Persona,
    selected: Boolean,
    imageId: Int,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) Gold else Color.DarkGray,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                CardDark,
                RoundedCornerShape(18.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = persona.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = persona.name,
            color = Gold,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = subtitle,
            color = Color.LightGray,
            fontSize = 10.sp,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) Gold else Color(0xFF34445B),
                contentColor = if (selected) Color.Black else Color.White
            )
        ) {
            Text(
                if (selected) "✓ SELECTED" else "SELECT"
            )
        }
    }
}
