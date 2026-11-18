
package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
        "welcome" -> WelcomeScreen(
            onSelect = { assistant ->
                selectedAssistant = assistant
                page = "customize"
            }
        )

        "customize" -> CustomizeScreen(
            assistant = selectedAssistant,
            initialName = userName,
            initialAppearance = appearance,
            onBack = { page = "welcome" },
            onContinue = { name, selectedAppearance ->
                userName = name
                appearance = selectedAppearance
                page = "home"
            }
        )

        "home" -> HomeScreen(
            assistant = selectedAssistant,
            userName = userName,
            appearance = appearance,
            onBack = { page = "welcome" }
        )
    }
}

// ---------------- WELCOME SCREEN ----------------

@Composable
fun WelcomeScreen(onSelect: (String) -> Unit) {

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
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // OFFICIAL LOGO HEADER

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(94.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "☰",
                    color = Blue,
                    fontSize = 29.sp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(R.drawable.app_logo),
                        contentDescription = "Ultron x Luna Logo",
                        modifier = Modifier.size(55.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        "Personal AI Assistant",
                        color = White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    "⚙",
                    color = Blue,
                    fontSize = 27.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "CHOOSE YOUR ASSISTANT",
                color = White,
                fontSize = 16.sp,
                letterSpacing = 2.sp,
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
                    imageRes = R.drawable.ultron_character,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelect("ULTRON") }
                )

                AssistantCard(
                    name = "LUNA",
                    tagline = "Kindness • Care • Support",
                    color = Purple,
                    imageRes = R.drawable.luna_character,
                    modifier = Modifier.weight(1f),
                    onClick = { onSelect("LUNA") }
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "✦  TOGETHER, ALWAYS...  ✦",
                color = White,
                fontSize = 14.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(18.dp))
        }
    }
}

// ---------------- CHARACTER CARDS ----------------

@Composable
fun AssistantCard(
    name: String,
    tagline: String,
    color: Color,
    imageRes: Int,
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
                        color.copy(alpha = 0.22f),
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
            .padding(8.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            if (name == "ULTRON") "◈" else "✧",
            color = color,
            fontSize = 29.sp
        )

        // REAL CHARACTER IMAGE

        Image(
            painter = painterResource(imageRes),
            contentDescription = "$name Character",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 5.dp)
                .clip(RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop
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

        Spacer(Modifier.height(7.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color.copy(alpha = 0.12f))
                .border(
                    1.5.dp,
                    color,
                    RoundedCornerShape(30.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {

            Text(
                "Select  ›",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ---------------- BACKGROUND ----------------

@Composable
fun BackgroundEnergy() {

    androidx.compose.foundation.Canvas(
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

        for (i in 0..55) {
            val x = (i * 83f) % size.width
            val y = (i * 137f) % size.height

            drawCircle(
                color = White.copy(alpha = 0.35f),
                radius = 1.2f,
                center = androidx.compose.ui.geometry.Offset(x, y)
            )
        }

        drawLine(
            color = Blue.copy(alpha = 0.55f),
            start = androidx.compose.ui.geometry.Offset(
                0f, size.height * 0.32f
            ),
            end = androidx.compose.ui.geometry.Offset(
                size.width * 0.48f, size.height * 0.32f
            ),
            strokeWidth = 2f
        )

        drawLine(
            color = Purple.copy(alpha = 0.55f),
            start = androidx.compose.ui.geometry.Offset(
                size.width * 0.52f, size.height * 0.32f
            ),
            end = androidx.compose.ui.geometry.Offset(
                size.width, size.height * 0.32f
            ),
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

        Text(
            "Choose Appearance",
            color = White,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(18.dp))

        listOf("Character", "Globe", "Energy Core").forEach { option ->

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
                    .clickable { appearance = option }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = appearance == option,
                    onClick = { appearance = option },
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
            onValueChange = { userName = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Enter your name") },
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
                onContinue(userName.trim(), appearance)
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "Ultron x Luna Logo",
            modifier = Modifier.size(75.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Personal AI Assistant",
            color = White,
            fontSize = 15.sp
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

        Spacer(Modifier.weight(1f))

        Text(
            "‹  Back to Assistant Selection",
            color = color,
            modifier = Modifier.clickable { onBack() }
        )
    }
}
