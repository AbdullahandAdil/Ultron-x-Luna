
package com.ultone.assistant.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.R
import com.ultone.assistant.data.Persona
import kotlinx.coroutines.delay
import java.util.Calendar

private val Gold = Color(0xFFFFD36E)
private val Dark = Color(0xFF080B14)
private val CardDark = Color(0xFF101827)
private val UltronCyan = Color(0xFF08C7F5)
private val LunaPurple = Color(0xFFB64CFF)
private val MutedText = Color(0xFF929DB2)

@Composable
fun CustomizeScreen(
    selectedPersona: Persona,
    userName: String,
    onPersonaSelected: (Persona) -> Unit,
    onNameChanged: (String) -> Unit,
    onSave: () -> Unit,
    onSettingsClick: () -> Unit
) {

    var currentHour by remember {
        mutableIntStateOf(
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            currentHour =
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        }
    }

    val greeting = when (currentHour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    val greetingIcon = when (currentHour) {
        in 5..16 -> "☀️"
        in 17..20 -> "🌇"
        else -> "🌙"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "☰",
                    color = Color.White,
                    fontSize = 29.sp
                )
            }

            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(60.dp)
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = onSettingsClick) {
                    Text(
                        text = "⚙",
                        color = Gold,
                        fontSize = 30.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "PERSONAL AI ASSISTANT",
            color = Gold,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(60.dp))

        // MAIN HEADING

        Text(
            text = "CHOOSE YOUR PERSONALITY",
            color = Color(0xFFE7ECF8),
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "One assistant. Two unique personalities.",
            color = MutedText,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(42.dp))

        // PERSONA CARDS

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
                accent = UltronCyan,
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
                accent = LunaPurple,
                onClick = {
                    onPersonaSelected(Persona.LUNA)
                }
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // GREETING PREVIEW

        GreetingPreviewCard(
            greeting = greeting,
            icon = greetingIcon,
            userName = userName
        )

        Spacer(modifier = Modifier.height(42.dp))

        // NAME INPUT

        Text(
            text = "WHAT SHOULD I CALL YOU?",
            color = Color(0xFFE7ECF8),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(26.dp))

        BasicTextField(
            value = userName,
            onValueChange = onNameChanged,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFFE7ECF8),
                fontSize = 19.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    CardDark,
                    RoundedCornerShape(22.dp)
                )
                .border(
                    1.5.dp,
                    Color(0xFF344158),
                    RoundedCornerShape(22.dp)
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 22.dp
                ),
            decorationBox = { innerTextField ->
                Box {
                    if (userName.isEmpty()) {
                        Text(
                            "Enter your name",
                            color = MutedText,
                            fontSize = 19.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(42.dp))

        // SAVE BUTTON

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .shadow(
                    12.dp,
                    RoundedCornerShape(22.dp),
                    ambientColor = Gold.copy(alpha = 0.25f),
                    spotColor = Gold.copy(alpha = 0.35f)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gold,
                contentColor = Color(0xFF15100A)
            ),
            shape = RoundedCornerShape(22.dp)
        ) {
            Text(
                text = "SAVE & CONTINUE",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // FOOTER

        Text(
            text = "TOGETHER, ALWAYS...",
            color = Gold,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "One Assistant. Two Personalities. Endless Possibilities.",
            color = MutedText,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(35.dp))
    }
}


// PERSONA CARD

@Composable
private fun PersonaChoiceCard(
    modifier: Modifier,
    persona: Persona,
    selected: Boolean,
    imageId: Int,
    subtitle: String,
    accent: Color,
    onClick: () -> Unit
) {

    val borderWidth by animateDpAsState(
        targetValue = if (selected) 2.5.dp else 1.dp,
        animationSpec = tween(300)
    )

    val borderColor by animateColorAsState(
        targetValue = if (selected) accent else Color(0xFF293449),
        animationSpec = tween(350)
    )

    val elevation by animateDpAsState(
        targetValue = if (selected) 13.dp else 2.dp,
        animationSpec = tween(350)
    )

    val infiniteTransition = rememberInfiniteTransition()

    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = modifier
            .graphicsLayer {
                if (selected) {
                    scaleX = glowPulse
                    scaleY = glowPulse
                }
            }
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(30.dp),
                ambientColor = accent.copy(alpha = 0.25f),
                spotColor = accent.copy(alpha = 0.30f)
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(30.dp)
            )
            .background(
                Color(0xFF0D1523),
                RoundedCornerShape(30.dp)
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp)
                .clip(RoundedCornerShape(23.dp))
        ) {

            Image(
                painter = painterResource(imageId),
                contentDescription = persona.name,
                modifier = Modifier.fillMaxSize()
            )

            if (selected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(42.dp)
                        .background(
                            accent,
                            RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        color = Color.Black,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = persona.name,
            color = accent,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = subtitle,
            color = Color(0xFFE0E5F0),
            fontSize = 10.sp,
            lineHeight = 17.sp,
            textAlign = TextAlign.Center,
            minLines = 2
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) accent else Color(0xFF26344A),
                contentColor = if (selected) Color.Black else Color.White
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = if (selected) "✓ SELECTED" else "SELECT",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}


// GREETING PREVIEW CARD

@Composable
private fun GreetingPreviewCard(
    greeting: String,
    icon: String,
    userName: String
) {

    val gradient = Brush.linearGradient(
        colors = listOf(
            UltronCyan,
            LunaPurple,
            Gold
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                gradient,
                RoundedCornerShape(34.dp)
            )
            .padding(1.5.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF101D31),
                            Color(0xFF171225)
                        )
                    ),
                    RoundedCornerShape(33.dp)
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 30.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "GREETING PREVIEW",
                color = MutedText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = icon,
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$greeting, ${userName.ifBlank { "Boss" }}!",
                color = Gold,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Your assistant is ready for you.",
                color = MutedText,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
