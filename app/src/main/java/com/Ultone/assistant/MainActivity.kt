
package com.ultone.assistant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Background = Color(0xFF050609)
private val SurfaceDark = Color(0xFF10141E)
private val Gold = Color(0xFFD9AE50)
private val UltronBlue = Color(0xFF00C8FF)
private val LunaPurple = Color(0xFFB56CFF)
private val SoftWhite = Color(0xFFECEEF5)
private val Muted = Color(0xFF9299A9)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AssistantApp()
        }
    }
}

@Composable
fun AssistantApp() {

    val context = androidx.compose.ui.platform.LocalContext.current

    val preferences = remember {
        context.getSharedPreferences(
            "ultron_luna_settings",
            Context.MODE_PRIVATE
        )
    }

    var activePersona by rememberSaveable {
        mutableStateOf(
            preferences.getString("persona", "") ?: ""
        )
    }

    var userName by rememberSaveable {
        mutableStateOf(
            preferences.getString("user_name", "") ?: ""
        )
    }

    var screen by rememberSaveable {
        mutableStateOf("splash")
    }

    LaunchedEffect(Unit) {
        delay(1100)

        screen = if (activePersona.isEmpty()) {
            "welcome"
        } else {
            "home"
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            when (screen) {

                "splash" -> SplashScreen()

                "welcome" -> WelcomeScreen(
                    onSelect = { persona ->
                        activePersona = persona

                        preferences.edit()
                            .putString("persona", persona)
                            .apply()

                        screen = "home"
                    }
                )

                "home" -> HomeScreen(
                    persona = activePersona,
                    userName = userName,
                    onSettings = {
                        screen = "settings"
                    }
                )

                "settings" -> SettingsScreen(
                    persona = activePersona,
                    userName = userName,
                    onNameChange = { newName ->
                        userName = newName

                        preferences.edit()
                            .putString("user_name", newName)
                            .apply()
                    },
                    onPersonaChange = { newPersona ->
                        activePersona = newPersona

                        preferences.edit()
                            .putString("persona", newPersona)
                            .apply()

                        screen = "home"
                    },
                    onBack = {
                        screen = "home"
                    }
                )
            }
        }
    }
}

// ---------------- SPLASH SCREEN ----------------

@Composable
fun SplashScreen() {

    var visible by remember {
        mutableStateOf(false)
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.75f,
        label = "logoScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "logoAlpha"
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "Ultron x Luna Logo",
                modifier = Modifier
                    .size(155.dp)
                    .scale(scale)
                    .alpha(alpha),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ULTRON  x  LUNA",
                color = Gold,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "PERSONAL AI ASSISTANT",
                color = Muted,
                fontSize = 10.sp,
                letterSpacing = 3.sp
            )
        }
    }
}

// ---------------- WELCOME SCREEN ----------------

@Composable
fun WelcomeScreen(
    onSelect: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 55.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "Crown Logo",
                modifier = Modifier.size(75.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Personal AI Assistant",
                color = SoftWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Choose your assistant",
                color = Muted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            PersonaCard(
                name = "ULTRON",
                description = "Professional  •  Intelligent  •  Powerful",
                color = UltronBlue,
                image = R.drawable.ultron_character,
                onClick = {
                    onSelect("ultron")
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            PersonaCard(
                name = "LUNA",
                description = "Friendly  •  Caring  •  Intelligent",
                color = LunaPurple,
                image = R.drawable.luna_character,
                onClick = {
                    onSelect("luna")
                }
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "TOGETHER, ALWAYS...",
                color = Gold,
                fontSize = 11.sp,
                letterSpacing = 3.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ---------------- PERSONA CARD ----------------

@Composable
fun PersonaCard(
    name: String,
    description: String,
    color: Color,
    image: Int,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF171C29),
                        Color(0xFF0C1018)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.65f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(image),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = name,
            color = color,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            color = Muted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(color.copy(alpha = 0.15f))
                .border(
                    1.dp,
                    color,
                    RoundedCornerShape(30.dp)
                )
                .padding(horizontal = 38.dp, vertical = 11.dp)
        ) {
            Text(
                text = "SELECT",
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}

// ---------------- HOME SCREEN ----------------

@Composable
fun HomeScreen(
    persona: String,
    userName: String,
    onSettings: () -> Unit
) {

    val isUltron = persona == "ultron"
    val accent = if (isUltron) UltronBlue else LunaPurple
    val name = if (isUltron) "ULTRON" else "LUNA"
    val greeting = if (isUltron) {
        "Systems online. How can I assist you?"
    } else {
        "Hello! I'm here for you."
    }

    var message by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(18.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Personal AI Assistant",
                    color = SoftWhite,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = name,
                    color = accent,
                    fontSize = 11.sp,
                    letterSpacing = 3.sp
                )
            }

            Text(
                text = "⚙",
                color = Gold,
                fontSize = 27.sp,
                modifier = Modifier.clickable {
                    onSettings()
                }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(
                        if (isUltron) {
                            R.drawable.ultron_character
                        } else {
                            R.drawable.luna_character
                        }
                    ),
                    contentDescription = name,
                    modifier = Modifier.size(230.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = if (userName.isBlank()) {
                        "Hello!"
                    } else {
                        "Hello, $userName!"
                    },
                    color = SoftWhite,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = greeting,
                    color = Muted,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "AI CONNECTION COMING SOON",
                    color = accent,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(SurfaceDark)
                .border(
                    1.dp,
                    accent.copy(alpha = 0.5f),
                    RoundedCornerShape(30.dp)
                )
                .padding(horizontal = 15.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "Message $name...",
                        color = Muted,
                        fontSize = 13.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = SoftWhite,
                    unfocusedTextColor = SoftWhite,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = accent
                ),
                maxLines = 3
            )

            Text(
                text = "➤",
                color = accent,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "AI responses will be enabled in the next development stage.",
            color = Muted,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ---------------- SETTINGS SCREEN ----------------

@Composable
fun SettingsScreen(
    persona: String,
    userName: String,
    onNameChange: (String) -> Unit,
    onPersonaChange: (String) -> Unit,
    onBack: () -> Unit
) {

    var name by rememberSaveable {
        mutableStateOf(userName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {

        Text(
            text = "‹  Settings",
            color = Gold,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Your Name",
            color = SoftWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                onNameChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Enter your name")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Change Assistant",
            color = SoftWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        SettingsPersonaButton(
            title = "ULTRON",
            color = UltronBlue,
            selected = persona == "ultron"
        ) {
            onPersonaChange("ultron")
        }

        Spacer(modifier = Modifier.height(14.dp))

        SettingsPersonaButton(
            title = "LUNA",
            color = LunaPurple,
            selected = persona == "luna"
        ) {
            onPersonaChange("luna")
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "ULTRON x LUNA",
            color = Gold,
            fontSize = 12.sp,
            letterSpacing = 3.sp
        )

        Text(
            text = "Version 1.0",
            color = Muted,
            fontSize = 11.sp
        )
    }
}

// ---------------- SETTINGS PERSONA BUTTON ----------------

@Composable
fun SettingsPersonaButton(
    title: String,
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceDark)
            .border(
                1.dp,
                if (selected) color else Color.DarkGray,
                RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(19.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = if (selected) "ACTIVE ✓" else "SELECT",
            color = if (selected) color else Muted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
