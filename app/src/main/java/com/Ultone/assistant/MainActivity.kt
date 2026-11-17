
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset

private val Dark = Color(0xFF030713)
private val Gold = Color(0xFFFFD36A)
private val Blue = Color(0xFF00D9FF)
private val Purple = Color(0xFFD044FF)

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
    var selected by remember { mutableStateOf("") }
    var page by remember { mutableStateOf("welcome") }

    when (page) {
        "welcome" -> WelcomeScreen(
            onSelect = {
                selected = it
                page = "customize"
            }
        )
        "customize" -> CustomizeScreen(
            assistant = selected,
            onBack = { page = "welcome" },
            onContinue = { page = "home" }
        )
        else -> HomeScreen(
            assistant = selected,
            onBack = { page = "welcome" }
        )
    }
}

@Composable
fun WelcomeScreen(onSelect: (String) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
    ) {
        val cardHeight = (maxHeight * 0.59f).coerceAtMost(510.dp)

        BackgroundEnergy()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("☰", color = Blue, fontSize = 30.sp)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "ULTRON",
                        color = Gold,
                        fontSize =  thirty(),
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        "AI Assistant",
                        color = Color.White,
                        fontSize = 19.sp
                    )
                }
                Text("⚙", color = Blue, fontSize = 29.sp)
            }

            Spacer(Modifier.height(18.dp))

            Text(
                "CHOOSE YOUR ASSISTANT",
                color = Color.White,
                fontSize = 17.sp,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(22.dp))

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
                    onClick = { onSelect("ULTRON") }
                )

                AssistantCard(
                    name = "LUNA",
                    tagline = "Kindness • Care • Support",
                    color = Purple,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelect("LUNA") }
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "✦  TOGETHER, ALWAYS...  ✦",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

fun thirty() = 30.sp

@Composable
fun AssistantCard(
    name: String,
    tagline: String,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        color.copy(alpha = 0.16f),
                        Color(0xFF080D20),
                        color.copy(alpha = 0.10f)
                    )
                )
            )
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            if (name == "ULTRON") "◈" else "✧",
            color = color,
            fontSize = 36.sp
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color.copy(alpha = 0.10f))
                .border(1.5.dp, color, RoundedCornerShape(30.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Select  ›",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CharacterArtwork(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height * 0.43f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.48f), Color.Transparent),
                    center = center,
                    radius = size.width * 0.65f
                ),
                radius = size.width * 0.65f,
                center = center
            )
            drawCircle(
                color = color.copy(alpha = 0.7f),
                radius = size.width * 0.17f,
                center = Offset(center.x, center.y - size.height * 0.12f)
            )
            drawRoundRect(
                brush = Brush.verticalGradient(
                    listOf(color.copy(alpha = 0.65f), Color(0xFF071020))
                ),
                topLeft = Offset(size.width * 0.19f, size.height * 0.42f),
                size = androidx.compose.ui.geometry.Size(
                    size.width * 0.62f,
                    size.height * 0.48f
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(45f)
            )
        }
    }
}

@Composable
fun BackgroundEnergy() {
    Canvas(Modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFF020611), Color(0xFF101027), Color(0xFF030611))
            )
        )
        for (i in 0..45) {
            val x = (i * 83f) % size.width
            val y = (i * 137f) % size.height
            drawCircle(
                Color.White.copy(alpha = 0.35f),
                radius = 1.2f,
                center = Offset(x, y)
            )
        }
        drawLine(
            Blue.copy(alpha = 0.45f),
            Offset(0f, size.height * 0.32f),
            Offset(size.width * 0.48f, size.height * 0.32f),
            2f
        )
        drawLine(
            Purple.copy(alpha = 0.45f),
            Offset(size.width * 0.52f, size.height * 0.32f),
            Offset(size.width, size.height * 0.32f),
            2f
        )
    }
}

@Composable
fun CustomizeScreen(
    assistant: String,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    val color = if (assistant == "ULTRON") Blue else Purple
    var appearance by remember { mutableStateOf("Character") }
    var userName by remember { mutableStateOf("") }

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
                .clickable { onBack() }
        )

        Spacer(Modifier.height(30.dp))

        Text(
            "CUSTOMIZE $assistant",
            color = color,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(35.dp))

        Text("Choose Appearance", color = Color.White, fontSize = 20.sp)

        Spacer(Modifier.height(18.dp))

        listOf("Character", "Globe", "Energy Core").forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.12f))
                    .border(
                        1.dp,
                        if (appearance == it) color else Color.DarkGray,
                        RoundedCornerShape(14.dp)
                    )
                    .clickable { appearance = it }
                    .padding(17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = appearance == it,
                    onClick = { appearance = it },
                    colors = RadioButtonDefaults.colors(selectedColor = color)
                )
                Text(it, color = Color.White, fontSize = 17.sp)
            }
        }

        Spacer(Modifier.height(28.dp))

        Text(
            "What should $assistant call you?",
            color = Color.White,
            fontSize = 17.sp
        )

        Spacer(Modifier.height(12.dp))

        BasicTextField(
            value = userName,
            onValueChange = { userName = it },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF172035), RoundedCornerShape(12.dp))
                .padding(16.dp),
            decorationBox = { inner ->
                if (userName.isEmpty()) {
                    Text("Enter your name", color = Color.Gray)
                }
                inner()
            }
        )

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text("SAVE & CONTINUE", color = Dark, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HomeScreen(assistant: String, onBack: () -> Unit) {
    val color = if (assistant == "ULTRON") Blue else Purple

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(assistant, color = color, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        Text("Assistant selected", color = Color.White, fontSize = 20.sp)
        Spacer(Modifier.height(30.dp))
        Text("‹  Back", color = color, modifier = Modifier.clickable { onBack() })
    }
}
