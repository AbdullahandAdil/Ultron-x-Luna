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

        window.statusBarColor =
            android.graphics.Color.rgb(3, 7, 19)

        window.navigationBarColor =
            android.graphics.Color.rgb(3, 7, 19)

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
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (onBack != null) {
            Text(
                "‹ BACK",
                color = color,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onBack()
                }
            )
        } else {
            Text(
                "☰",
                color = color,
                fontSize = 25.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                "Personal AI Assistant",
                color = White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            "⚙",
            color = color,
            fontSize = 25.sp
        )
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

            Spacer(Modifier.height(18.dp))

            Text(
                "CHOOSE YOUR ASSISTANT",
                color = White,
                fontSize = 19.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(430.dp),
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

            Spacer(Modifier.height(30.dp))

            Text(
                "✦  TOGETHER, ALWAYS...  ✦",
                color = White,
                fontSize = 15.sp,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(20.dp))
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
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            if (name == "ULTRON") "◈" else "✧",
            color = color,
            fontSize = 28.sp
        )

        Image(
            painter = painterResource(image),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(18.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            name,
            color = color,
            fontSize = 23.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            tagline,
            color = color,
            fontSize = 9.sp,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color.copy(alpha = 0.18f)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                "Select  ›",
                color = White,
                fontSize = 17.sp
            )
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

    BoxWithConstraints(
        Modifier.fillMaxSize()
    ) {
        AppBackground()

        val compact = maxHeight < 750.dp
        val artworkHeight = if (compact) 245.dp else 285.dp

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader(color, onBack)

            Spacer(
                Modifier.height(
                    if (compact) 6.dp else 12.dp
                )
            )

            Text(
                "CUSTOMIZE YOUR $assistant",
                color = color,
                fontSize = if (compact) 19.sp else 22.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                Modifier.height(
                    if (compact) 12.dp else 18.dp
                )
            )

            // ARTWORK AND APPEARANCE OPTIONS

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(artworkHeight),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Box(
                    Modifier
                        .weight(1.05f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(18.dp))
                        .background(color.copy(alpha = 0.08f))
                        .border(
                            1.dp,
                            color,
                            RoundedCornerShape(18.dp)
                        )
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
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF070D1D))
                        .border(
                            1.dp,
                            color,
                            RoundedCornerShape(18.dp)
                        )
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Choose Appearance",
                        color = White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(4.dp))

                    AppearanceOption(
                        title = "Character",
                        subtitle = "Faceless Hero",
                        selected = appearance == "Character",
                        color = color,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            appearance = "Character"
                        }
                    ) {
                        CharacterPreview(assistant)
                    }

                    AppearanceOption(
                        title = "Globe",
                        subtitle = "Dynamic Planet",
                        selected = appearance == "Globe",
                        color = color,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            appearance = "Globe"
                        }
                    ) {
                        GlobeArtwork(
                            color,
                            Modifier.fillMaxSize()
                        )
                    }

                    AppearanceOption(
                        title = "Energy Core",
                        subtitle = "Power Energy",
                        selected = appearance == "Energy Core",
                        color = color,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            appearance = "Energy Core"
                        }
                    ) {
                        EnergyCoreArtwork(
                            color,
                            Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(Modifier.height(15.dp))

            Text(
                "What should $assistant call you?",
                color = White,
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(7.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text("Enter Your Name...")
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = color,
                    unfocusedBorderColor = color.copy(alpha = 0.5f),
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = color
                )
            )

            Spacer(Modifier.height(12.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(17.dp))
                    .background(color.copy(alpha = 0.10f))
                    .border(
                        1.dp,
                        color,
                        RoundedCornerShape(17.dp)
                    )
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Your Name Preview",
                    color = color,
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    if (userName.isNotBlank()) {
                        "Hello, ${userName.trim()}!"
                    } else {
                        "Hello!"
                    },
                    color = White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(15.dp))

            Button(
                onClick = {
                    onContinue(userName.trim(), appearance)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    "✓   SAVE & CONTINUE   ›",
                    color = Dark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                "Together, Always...",
                color = color,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}


// ---------------- APPEARANCE OPTION ----------------

@Composable
fun AppearanceOption(
    title: String,
    subtitle: String,
    selected: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    artwork: @Composable () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) {
                    color.copy(alpha = 0.18f)
                } else {
                    Color(0xFF030713)
                }
            )
            .border(
                if (selected) 2.dp else 1.dp,
                if (selected) color else Color.DarkGray,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            artwork()

            if (selected) {
                Text(
                    "✓",
                    color = color,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }

        Text(
            title,
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            subtitle,
            color = color,
            fontSize = 8.sp,
            textAlign = TextAlign.Center
        )
    }
}

// ---------------- CHARACTER PREVIEW ----------------

@Composable
fun CharacterPreview(assistant: String) {
    Image(
        painter = painterResource(
            if (assistant == "ULTRON") {
                R.drawable.ultron_character
            } else {
                R.drawable.luna_character
            }
        ),
        contentDescription = "Character",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

// ---------------- SELECTED ARTWORK ----------------

@Composable
fun SelectedArtwork(
    assistant: String,
    appearance: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        when (appearance) {
            "Character" -> CharacterPreview(assistant)

            "Globe" -> GlobeArtwork(
                if (assistant == "ULTRON") Blue else Purple,
                Modifier.fillMaxSize()
            )

            "Energy Core" -> EnergyCoreArtwork(
                if (assistant == "ULTRON") Blue else Purple,
                Modifier.fillMaxSize()
            )
        }
    }
}

// ---------------- GLOBE ARTWORK ----------------

@Composable
fun GlobeArtwork(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier) {
        val radius = size.minDimension * 0.32f
        val center = Offset(
            size.width / 2,
            size.height / 2
        )

        // Outer glow
        drawCircle(
            brush = Brush.radialGradient(
                listOf(
                    color.copy(alpha = 0.38f),
                    Color.Transparent
                ),
                center = center,
                radius = radius * 2.1f
            ),
            radius = radius * 2.1f,
            center = center
        )

        // Planet
        drawCircle(
            brush = Brush.linearGradient(
                listOf(
                    Color.White.copy(alpha = 0.65f),
                    color,
                    Color(0xFF06133C)
                ),
                start = Offset(
                    center.x - radius,
                    center.y - radius
                ),
                end = Offset(
                    center.x + radius,
                    center.y + radius
                )
            ),
            radius = radius,
            center = center
        )

        // Planet details
        val land = color.copy(alpha = 0.85f)

        val path = Path().apply {
            moveTo(
                center.x - radius * 0.65f,
                center.y - radius * 0.25f
            )
            lineTo(
                center.x - radius * 0.25f,
                center.y - radius * 0.65f
            )
            lineTo(
                center.x + radius * 0.05f,
                center.y - radius * 0.35f
            )
            lineTo(
                center.x - radius * 0.05f,
                center.y + radius * 0.05f
            )
            lineTo(
                center.x - radius * 0.42f,
                center.y + radius * 0.20f
            )
            close()
        }

        drawPath(path, land)

        val secondLand = Path().apply {
            moveTo(
                center.x + radius * 0.25f,
                center.y - radius * 0.10f
            )
            lineTo(
                center.x + radius * 0.65f,
                center.y - radius * 0.30f
            )
            lineTo(
                center.x + radius * 0.55f,
                center.y + radius * 0.25f
            )
            lineTo(
                center.x + radius * 0.20f,
                center.y + radius * 0.55f
            )
            lineTo(
                center.x + radius * 0.05f,
                center.y + radius * 0.15f
            )
            close()
        }

        drawPath(
            secondLand,
            land.copy(alpha = 0.65f)
        )

        // Orbit ring
        drawOval(
            color = color.copy(alpha = 0.95f),
            topLeft = Offset(
                center.x - radius * 1.55f,
                center.y - radius * 0.30f
            ),
            size = androidx.compose.ui.geometry.Size(
                radius * 3.1f,
                radius * 0.65f
            ),
            style = Stroke(width = 1.dp.toPx())
        )

        // Orbit light
        drawCircle(
            color = White,
            radius = 3.dp.toPx(),
            center = Offset(
                center.x + radius * 1.35f,
                center.y - radius * 0.30f
            )
        )
    }
}

// ---------------- ENERGY CORE ARTWORK ----------------

@Composable
fun EnergyCoreArtwork(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier) {
        val center = Offset(
            size.width / 2,
            size.height / 2
        )

        val radius = size.minDimension * 0.30f

        // Outer glow
        drawCircle(
            brush = Brush.radialGradient(
                listOf(
                    color.copy(alpha = 0.65f),
                    color.copy(alpha = 0.20f),
                    Color.Transparent
                ),
                center = center,
                radius = radius * 2.2f
            ),
            radius = radius * 2.2f,
            center = center
        )

        // Outer energy rings
        for (i in 1..3) {
            drawCircle(
                color = color.copy(alpha = 0.65f / i),
                radius = radius * (1f + i * 0.35f),
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Main energy core
        drawCircle(
            brush = Brush.radialGradient(
                listOf(
                    Color.White,
                    color,
                    color.copy(alpha = 0.35f),
                    Color.Transparent
                ),
                center = center,
                radius = radius
            ),
            radius = radius,
            center = center
        )

        // Inner light
        drawCircle(
            color = White,
            radius = 4.dp.toPx(),
            center = center
        )
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

    Box(Modifier.fillMaxSize()) {
        AppBackground()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader(color)

            Spacer(Modifier.height(25.dp))

            Text(
                assistant,
                color = color,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(Modifier.height(16.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(color.copy(alpha = 0.08f))
                    .border(
                        1.dp,
                        color,
                        RoundedCornerShape(25.dp)
                    )
            ) {
                SelectedArtwork(
                    assistant = assistant,
                    appearance = appearance,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(22.dp))

            Text(
                if (userName.isNotBlank()) {
                    "Hello, $userName!"
                } else {
                    "Hello!"
                },
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(28.dp))

            Text(
                "‹  Back to Assistant Selection",
                color = color,
                modifier = Modifier.clickable {
                    onBack()
                }
            )
        }
    }
}
