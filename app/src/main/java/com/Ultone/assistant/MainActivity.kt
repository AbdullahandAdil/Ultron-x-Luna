package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
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
    var assistant by remember { mutableStateOf("ULTRON") }
    var userName by remember { mutableStateOf("") }
    var appearance by remember { mutableStateOf("Character") }

    when (page) {
        "welcome" -> WelcomeScreen(
            onSelect = {
                assistant = it
                page = "customize"
            }
        )

        "customize" -> CustomizeScreen(
            assistant = assistant,
            initialName = userName,
            initialAppearance = appearance,
            onBack = { page = "welcome" },
            onContinue = { name, selected ->
                userName = name
                appearance = selected
                page = "home"
            }
        )

        "home" -> HomeScreen(
            assistant = assistant,
            userName = userName,
            appearance = appearance,
            onBack = { page = "welcome" }
        )
    }
}

// ---------------- BACKGROUND ----------------

@Composable
fun AppBackground() {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF020611),
                        Color(0xFF101027),
                        Color(0xFF030611)
                    )
                )
            )
    ) {
        Canvas(Modifier.fillMaxSize()) {
            for (i in 0..65) {
                val x = (i * 83f) % size.width
                val y = (i * 137f) % size.height

                drawCircle(
                    Color.White.copy(alpha = 0.28f),
                    radius = 1.2f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

// ---------------- HEADER ----------------

@Composable
fun AppHeader(
    color: Color,
    onBack: (() -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (onBack != null) {
            Text(
                "‹ BACK",
                color = color,
                fontSize = 18.sp,
                modifier = Modifier.clickable { onBack() }
            )
        } else {
            Text("☰", color = color, fontSize = 27.sp)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(58.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                "Personal AI Assistant",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text("⚙", color = color, fontSize = 27.sp)
    }
}

// ---------------- WELCOME SCREEN ----------------

@Composable
fun WelcomeScreen(onSelect: (String) -> Unit) {
    Box(Modifier.fillMaxSize()) {
        AppBackground()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader(Blue)

            Spacer(Modifier.height(25.dp))

            Text(
                "CHOOSE YOUR ASSISTANT",
                color = White,
                fontSize = 19.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AssistantCard(
                    name = "ULTRON",
                    tagline = "Power • Logic • Intelligence",
                    color = Blue,
                    image = R.drawable.ultron_character,
                    modifier = Modifier.weight(1f)
                ) {
                    onSelect("ULTRON")
                }

                AssistantCard(
                    name = "LUNA",
                    tagline = "Kindness • Care • Support",
                    color = Purple,
                    image = R.drawable.luna_character,
                    modifier = Modifier.weight(1f)
                ) {
                    onSelect("LUNA")
                }
            }

            Spacer(Modifier.height(45.dp))

            Text(
                "✦  TOGETHER, ALWAYS...  ✦",
                color = White,
                fontSize = 15.sp,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(25.dp))
        }
    }
}

// ---------------- ASSISTANT CARD ----------------

@Composable
fun AssistantCard(
    name: String,
    tagline: String,
    color: Color,
    image: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(25.dp))
            .background(color.copy(alpha = 0.13f))
            .border(2.dp, color, RoundedCornerShape(25.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            if (name == "ULTRON") "◈" else "✧",
            color = color,
            fontSize = 32.sp
        )

        Image(
            painter = painterResource(image),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            name,
            color = color,
            fontSize = 25.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            tagline,
            color = color,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color.copy(alpha = 0.18f)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("Select  ›", color = White, fontSize = 18.sp)
        }
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

    Box(Modifier.fillMaxSize()) {
        AppBackground()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            AppHeader(color, onBack)

            Spacer(Modifier.height(12.dp))

            Text(
                "CUSTOMIZE YOUR $assistant",
                color = color,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // MAIN ARTWORK AND OPTIONS

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(370.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                        .weight(1.05f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(color.copy(alpha = 0.08f))
                        .border(1.dp, color, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    SelectedArtwork(
                        assistant = assistant,
                        appearance = appearance,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    Modifier
                        .weight(0.95f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF070D1D))
                        .border(1.dp, color, RoundedCornerShape(20.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Choose Appearance",
                        color = White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(8.dp))

                    AppearanceOption(
                        title = "Character",
                        subtitle = "Faceless Hero",
                        selected = appearance == "Character",
                        color = color,
                        onClick = { appearance = "Character" }
                    ) {
                        CharacterPreview(assistant)
                    }

                    AppearanceOption(
                        title = "Globe",
                        subtitle = "Dynamic Planet",
                        selected = appearance == "Globe",
                        color = color,
                        onClick = { appearance = "Globe" }
                    ) {
                        GlobeArtwork(color, Modifier.fillMaxSize())
                    }

                    AppearanceOption(
                        title = "Energy Core",
                        subtitle = "Power Energy",
                        selected = appearance == "Energy Core",
                        color = color,
                        onClick = { appearance = "Energy Core" }
                    ) {
                        EnergyCoreArtwork(color, Modifier.fillMaxSize())
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            Text(
                "What should $assistant call you?",
                color = White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Enter Your Name...") },
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = color,
                    unfocusedBorderColor = color.copy(alpha = 0.5f),
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = color
                )
            )

            Spacer(Modifier.height(18.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color.copy(alpha = 0.10f))
                    .border(1.5.dp, color, RoundedCornerShape(20.dp))
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Your Name Preview", color = color, fontSize = 17.sp)

                Spacer(Modifier.height(8.dp))

                Text(
                    if (userName.isNotBlank()) {
                        "Hello, ${userName.trim()}!"
                    } else {
                        "Hello!"
                    },
                    color = White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {
                    onContinue(userName.trim(), appearance)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color
                ),
                shape = RoundedCornerShape(35.dp)
            ) {
                Text(
                    "✓   SAVE & CONTINUE   ›",
                    color = Dark,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(22.dp))

            Text(
                "Together, Always...",
                color = color,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(25.dp))
        }
    }
}