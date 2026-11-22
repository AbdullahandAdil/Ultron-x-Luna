
package com.ultone.assistant.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    // Screen khulte hi koi persona selected nahi hoga.
    var chosenPersona by remember {
        mutableStateOf<Persona?>(null)
    }

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
                modifier = Modifier.size(54.dp)
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(42.dp))

        // MAIN HEADING

        Text(
            text = "CHOOSE YOUR PERSONALITY",
            color = Color(0xFFE7ECF8),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "One assistant. Two unique personalities.",
            color = MutedText,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // PERSONA CARDS

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PersonaChoiceCard(
                modifier = Modifier.weight(1f),
                persona = Persona.ULTRON,
                selected = chosenPersona == Persona.ULTRON,
                imageId = R.drawable.ultron_character,
                subtitle = "Professional • Intelligent • Powerful",
                accent = UltronCyan,
                onClick = {
                    chosenPersona = Persona.ULTRON
                    onPersonaSelected(Persona.ULTRON)
                }
            )

            PersonaChoiceCard(
                modifier = Modifier.weight(1f),
                persona = Persona.LUNA,
                selected = chosenPersona == Persona.LUNA,
                imageId = R.drawable.luna_character,
                subtitle = "Caring • Intelligent • Friendly",
                accent = LunaPurple,
                onClick = {
                    chosenPersona = Persona.LUNA
                    onPersonaSelected(Persona.LUNA)
                }
            )
        }

        Spacer(modifier = Modifier.height(34.dp))

        // GREETING PREVIEW

        GreetingPreviewCard(
            greeting = greeting,
            icon = greetingIcon,
            userName = userName
        )

        Spacer(modifier = Modifier.height(32.dp))

        // NAME INPUT

        Text(
            text = "WHAT SHOULD I CALL YOU?",
            color = Color(0xFFE7ECF8),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = userName,
            onValueChange = onNameChanged,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFFE7ECF8),
                fontSize = 18.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    CardDark,
                    RoundedCornerShape(18.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF344158),
                    RoundedCornerShape(18.dp)
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 19.dp
                ),
            decorationBox = { innerTextField ->
                Box {
                    if (userName.isEmpty()) {
                        Text(
                            "Enter your name",
                            color = MutedText,
                            fontSize = 18.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        // SAVE BUTTON

        Button(
            onClick = {
                if (chosenPersona != null) {
                    onSave()
                }
            },
            enabled = chosenPersona != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = Gold.copy(alpha = 0.20f),
                    spotColor = Gold.copy(alpha = 0.25f)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gold,
                contentColor = Color(0xFF15100A),
                disabledContainerColor = Color(0xFF394052),
                disabledContentColor = Color(0xFF929DB2)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "SAVE & CONTINUE",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }

        Spacer(modifier = Modifier.height(38.dp))

        // FOOTER

        Text(
            text = "TOGETHER, ALWAYS...",
            color = Gold,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.5.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "One Assistant. Two Personalities. Endless Possibilities.",
            color = MutedText,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))
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
    val lift by animateDpAsState(
        targetValue = if (selected) (-9).dp else 0.dp,
        animationSpec = tween(300),
        label = "cardLift"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (selected) 2.dp else 1.dp,
        animationSpec = tween(300),
        label = "cardBorderWidth"
    )

    val borderColor by animateColorAsState(
        targetValue = if (selected) accent else Color(0xFF293449),
        animationSpec = tween(300),
        label = "cardBorderColor"
    )

    val elevation by animateDpAsState(
        targetValue = if (selected) 15.dp else 3.dp,
        animationSpec = tween(300),
        label = "cardElevation"
    )

    Column(
        modifier = modifier
            .offset(y = lift)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(24.dp),
                ambientColor = accent.copy(alpha = 0.20f),
                spotColor = accent.copy(alpha = 0.25f)
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                Color(0xFF0D1523),
                RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // PHOTO WITH SOFT ROUNDED CORNERS

        Image(
            painter = painterResource(imageId),
            contentDescription = persona.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .clip(RoundedCornerShape(13.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = persona.name,
            color = accent,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(7.dp))

        // SUBTITLE IN ONE LINE

        Text(
            text = subtitle,
            color = Color(0xFFE0E5F0),
            fontSize = 8.sp,
            lineHeight = 11.sp,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(13.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) accent else Color(0xFF26344A),
                contentColor = if (selected) Color.Black else Color.White
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = if (selected) "✓ SELECTED" else "SELECT",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
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

    val floatingTransition = rememberInfiniteTransition()

    val floatingOffset by floatingTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = floatingOffset
            }
            .background(
                gradient,
                RoundedCornerShape(28.dp)
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
                    RoundedCornerShape(27.dp)
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 23.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GREETING PREVIEW",
                color = MutedText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = icon,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$greeting, ${userName.ifBlank { "Boss" }}!",
                color = Gold,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Your assistant is ready for you.",
                color = MutedText,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
