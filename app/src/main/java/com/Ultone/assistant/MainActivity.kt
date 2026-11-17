
package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

private val Dark = Color(0xFF030713)
private val Gold = Color(0xFFFFD36A)
private val Blue = Color(0xFF00D9FF)
private val Purple = Color(0xFFD044FF)
private val White = Color.White

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = android.graphics.Color.rgb(3, 7, 19)
        window.navigationBarColor = android.graphics.Color.rgb(3, 7, 19)

        setContent {
            MaterialTheme {
                AssistantApp()
            }
        }
    }
}

@Composable
fun AssistantApp() {

    var page by remember { mutableStateOf("welcome") }
    var selectedAssistant by remember { mutableStateOf("ULTRON") }
    var userName by remember { mutableStateOf("") }
    var appearance by remember { mutableStateOf("Character") }

    when (page) {

        "welcome" -> {
            WelcomeScreen(
                onSelect = { assistant ->
                    selectedAssistant = assistant
                    page = "customize"
                }
            )
        }

        "customize" -> {
            CustomizeScreen(
                assistant = selectedAssistant,
                initialName = userName,
                initialAppearance = appearance,
                onBack = {
                    page = "welcome"
                },
                onContinue = { name, selectedAppearance ->
                    userName = name
                    appearance = selectedAppearance
                    page = "home"
                }
            )
        }

        "home" -> {
            HomeScreen(
                assistant = selectedAssistant,
                userName = userName,
                appearance = appearance,
                onBack = {
                    page = "welcome"
                }
            )
        }
    }
}

// ---------------- WELCOME SCREEN ----------------

@Composable
fun WelcomeScreen(
    onSelect: (String) -> Unit
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
    ) {

        val cardHeight = (maxHeight * 0.57f)
            .coerceAtMost(500.dp)

        BackgroundEnergy()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // HEADER

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "☰",
                    color = Blue,
                    fontSize = 30.sp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "ULTRON",
                        color = Gold,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        "AI Assistant",
                        color = White,
                        fontSize = 18.sp
                    )
                }

                Text(
                    "⚙",
                    color = Blue,
                    fontSize = 29.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            // TITLE

            Text(
                "CHOOSE YOUR ASSISTANT",
                color = White,
                fontSize = 16.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // CHARACTER CARDS

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                AssistantCard(
                    name = "ULTRON",
                    tagline = "Power • Logic • Intelligence",
                    color = Blue,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelect("ULTRON")
                    }
                )

                AssistantCard(
                    name = "LUNA",
                    tagline = "Kindness • Care • Support",
                    color = Purple,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelect("LUNA")
                    }
                )
            }

            Spacer(Modifier.weight(1f))

            // FOOTER

            Text(
                "✦  TOGETHER, ALWAYS...  ✦",
                color = White,
                fontSize = 14.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ---------------- ASSISTANT CARD ----------------

@Composable
fun AssistantCard(
    name: String,
    tagline: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        color.copy(alpha = 0.20f),
                        Color(0xFF080D20),
                        color.copy(alpha = 0.10f)
                    )
                )
            )
            .border(
                2.dp,
                color,
                RoundedCornerShape(22.dp)
            )
            .padding(9.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            if (name == "ULTRON") "◈" else "✧",
            color = color,
            fontSize = 35.sp
        )

        CharacterArtwork(
            color = color,
            modifier = Modifier.weight(1f)
        )

        Text(
            name,
            color = color,
            fontSize = 23.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )

        Text(
            tagline,
            color = color,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color.copy(alpha = 0.12f))
                .border(
                    1.5.dp,
                    color,
                    RoundedCornerShape(30.dp)
                )
                .clickable {
                    onClick()
                },

            contentAlignment = Alignment.Center
        ) {

            Text(
                "Select  ›",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ---------------- CHARACTER PLACEHOLDER ----------------

@Composable
fun CharacterArtwork(
    color: Color,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val center = Offset(
                size.width / 2,
                size.height * 0.35f
            )

            // Glow

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.50f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = size.width * 0.65f
                ),
                radius = size.width * 0.65f,
                center = center
            )

            // Head

            drawCircle(
                color = color.copy(alpha = 0.85f),
                radius = size.width * 0.17f,
                center = Offset(
                    center.x,
                    center.y
                )
            )

            // Body

            drawRoundRect(
                brush = Brush.verticalGradient(
                    listOf(
                        color.copy(alpha = 0.65f),
                        Color(0xFF071020)
                    )
                ),
                topLeft = Offset(
                    size.width * 0.19f,
                    size.height * 0.48f
                ),
                size = Size(
                    size.width * 0.62f,
                    size.height * 0.48f
                ),
                cornerRadius = CornerRadius(45f)
            )

            // Energy lines

            drawLine(
                color = color.copy(alpha = 0.7f),
                start = Offset(0f, size.height * 0.72f),
                end = Offset(size.width * 0.28f, size.height * 0.58f),
                strokeWidth = 3f
            )

            drawLine(
                color = color.copy(alpha = 0.7f),
                start = Offset(size.width, size.height * 0.72f),
                end = Offset(size.width * 0.72f, size.height * 0.58f),
                strokeWidth = 3f
            )
        }
    }
}

// ---------------- BACKGROUND ----------------

@Composable
fun BackgroundEnergy() {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        drawRect(
            brush = Brush.verticalGradient(
                listOf(
                    Color(0xFF020611),
                    Color(0xFF101027),
                    Color(0xFF030611)
                )
            )
        )

        // Stars

        for (i in 0..55) {

            val x = (i * 83f) % size.width
            val y = (i * 137f) % size.height

            drawCircle(
                color = White.copy(alpha = 0.35f),
                radius = 1.2f,
                center = Offset(x, y)
            )
        }

        // Blue energy line

        drawLine(
            color = Blue.copy(alpha = 0.55f),
            start = Offset(0f, size.height * 0.32f),
            end = Offset(size.width * 0.48f, size.height * 0.32f),
            strokeWidth = 2f
        )

        // Purple energy line

        drawLine(
            color = Purple.copy(alpha = 0.55f),
            start = Offset(size.width * 0.52f, size.height * 0.32f),
            end = Offset(size.width, size.height * 0.32f),
            strokeWidth = 2f
        )
    }
}

// ---------------- CUSTOMIZATION SCREEN ----------------

@Composable
fun CustomizeScreen(
    assistant: String,
    initialName: String,
    initialAppearance: String,
    onBack: () -> Unit,
    onContinue: (String, String) -> Unit
) {

    val color = if (assistant == "ULTRON") Blue else Purple

    var appearance by remember(assistant) {
        mutableStateOf(initialAppearance)
    }

    var userName by remember(assistant) {
        mutableStateOf(initialName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(22.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "‹  BACK",
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onBack()
                }
        )

        Spacer(Modifier.height(30.dp))

        Text(
            "CUSTOMIZE $assistant",
            color = color,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(35.dp))

        Text(
            "Choose Appearance",
            color = White,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(18.dp))

        // APPEARANCE OPTIONS

        listOf(
            "Character",
            "Globe",
            "Energy Core"
        ).forEach { option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.12f))
                    .border(
                        1.dp,
                        if (appearance == option) color else Color.DarkGray,
                        RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        appearance = option
                    }
                    .padding(12.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = appearance == option,
                    onClick = {
                        appearance = option
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = color
                    )
                )

                Text(
                    option,
                    color = White,
                    fontSize = 17.sp
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        Text(
            "What should $assistant call you?",
            color = White,
            fontSize = 17.sp
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Enter your name")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = color,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = White,
                unfocusedTextColor = White,
                focusedLabelColor = color,
                unfocusedLabelColor = Color.Gray,
                cursorColor = color
            )
        )

        Spacer(Modifier.height(20.dp))

        if (userName.isNotBlank()) {

            Text(
                "Hello, ${userName.trim()}!",
                color = color,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                onContinue(
                    userName.trim(),
                    appearance
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color
            )
        ) {

            Text(
                "SAVE & CONTINUE",
                color = Dark,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ---------------- HOME SCREEN ----------------

@Composable
fun HomeScreen(
    assistant: String,
    userName: String,
    appearance: String,
    onBack: () -> Unit
) {

    val color = if (assistant == "ULTRON") Blue else Purple

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(22.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "ULTRON AI ASSISTANT",
            color = Gold,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(45.dp))

        Text(
            assistant,
            color = color,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(Modifier.height(15.dp))

        Text(
            appearance,
            color = White,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(20.dp))

        Text(
            if (userName.isNotBlank()) {
                "Hello, $userName!"
            } else {
                "Hello!"
            },
            color = White,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(45.dp))

        Text(
            "‹  Back to Assistant Selection",
            color = color,
            modifier = Modifier.clickable {
                onBack()
            }
        )
    }
}
