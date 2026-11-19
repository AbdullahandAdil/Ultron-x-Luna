
package com.ultone.assistant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Background = Color(0xFF03050B)
private val Panel = Color(0xFF101522)
private val Gold = Color(0xFFFFC94A)
private val Blue = Color(0xFF00C8FF)
private val Purple = Color(0xFFB45CFF)
private val White = Color(0xFFF4F5FA)
private val Muted = Color(0xFF969DB0)

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

    val prefs = remember {
        context.getSharedPreferences(
            "ultron_luna_settings",
            Context.MODE_PRIVATE
        )
    }

    var persona by rememberSaveable {
        mutableStateOf(
            prefs.getString("persona", "") ?: ""
        )
    }

    var userName by rememberSaveable {
        mutableStateOf(
            prefs.getString("user_name", "") ?: ""
        )
    }

    var screen by rememberSaveable {
        mutableStateOf("splash")
    }

    LaunchedEffect(Unit) {
        delay(1100)
        screen = if (persona.isBlank()) "welcome" else "home"
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            when (screen) {

                "splash" -> SplashScreen()

                "welcome" -> WelcomeScreen(
                    selectedPersona = persona,
                    userName = userName,
                    onNameChange = { userName = it },
                    onSelect = { persona = it },
                    onSettings = { screen = "settings" },
                    onContinue = {
                        prefs.edit()
                            .putString("persona", persona)
                            .putString("user_name", userName.trim())
                            .apply()

                        userName = userName.trim()
                        screen = "home"
                    }
                )

                "home" -> HomeScreen(
                    persona = persona,
                    userName = userName,
                    onSettings = { screen = "settings" }
                )

                "settings" -> SettingsScreen(
                    persona = persona,
                    userName = userName,
                    onNameChange = {
                        userName = it
                        prefs.edit()
                            .putString("user_name", it)
                            .apply()
                    },
                    onPersonaChange = {
                        persona = it
                        prefs.edit()
                            .putString("persona", it)
                            .apply()
                        screen = "home"
                    },
                    onBack = {
                        screen = if (persona.isBlank()) {
                            "welcome"
                        } else {
                            "home"
                        }
                    }
                )
            }
        }
    }
}

// SPLASH SCREEN

@Composable
fun SplashScreen() {

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
                contentDescription = "Crown Logo",
                modifier = Modifier.size(145.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "ULTRON x LUNA",
                color = Gold,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "PERSONAL AI ASSISTANT",
                color = Muted,
                fontSize = 10.sp,
                letterSpacing = 3.sp
            )
        }
    }
}

// WELCOME SCREEN

@Composable
fun WelcomeScreen(
    selectedPersona: String,
    userName: String,
    onNameChange: (String) -> Unit,
    onSelect: (String) -> Unit,
    onSettings: () -> Unit,
    onContinue: () -> Unit
) {

    var menuExpanded by remember {
        mutableStateOf(false)
    }

    val accent = when (selectedPersona) {
        "ultron" -> Blue
        "luna" -> Purple
        else -> Gold
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        // HEADER

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 22.dp)
        ) {

            Box(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                HeaderIcon(
                    symbol = "☰",
                    onClick = { menuExpanded = true }
                )

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = {
                        menuExpanded = false
                    },
                    containerColor = Panel
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("About Assistant", color = White)
                        },
                        onClick = { menuExpanded = false }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Settings", color = White)
                        },
                        onClick = {
                            menuExpanded = false
                            onSettings()
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(70.dp)
                )

                Text(
                    "Personal AI Assistant",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                HeaderIcon(
                    symbol = "⚙",
                    onClick = onSettings
                )
            }
        }

        Text(
            "CHOOSE YOUR ASSISTANT",
            color = Gold,
            fontSize = 11.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textAlign = TextAlign.Center
        )

        // TWO CARDS

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            PersonaSelectionCard(
                name = "ULTRON",
                description = "Professional",
                color = Blue,
                image = R.drawable.ultron_character,
                selected = selectedPersona == "ultron",
                modifier = Modifier.weight(1f),
                onClick = { onSelect("ultron") }
            )

            PersonaSelectionCard(
                name = "LUNA",
                description = "Friendly",
                color = Purple,
                image = R.drawable.luna_character,
                selected = selectedPersona == "luna",
                modifier = Modifier.weight(1f),
                onClick = { onSelect("luna") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Gold.copy(alpha = 0.6f))
            )

            Text("  ✦  ", color = Gold, fontSize = 20.sp)

            Box(
                Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Gold.copy(alpha = 0.6f))
            )
        }

        Text(
            "Your Name",
            color = White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Text(
            "Enter your name to continue",
            color = Muted,
            fontSize = 13.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        NameInputField(
            value = userName,
            onValueChange = onNameChange
        )

        Spacer(Modifier.height(18.dp))

        NamePreviewCard(
            persona = selectedPersona,
            userName = userName
        )

        Spacer(Modifier.height(22.dp))

        SaveContinueButton(
            enabled = selectedPersona.isNotBlank() &&
                    userName.isNotBlank(),
            color = accent,
            onClick = onContinue
        )

        Spacer(Modifier.height(23.dp))

        FooterSection()

        Spacer(Modifier.height(14.dp))
    }
}

// HEADER ICON

@Composable
fun HeaderIcon(
    symbol: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(43.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Panel)
            .border(
                1.dp,
                Gold.copy(alpha = 0.45f),
                RoundedCornerShape(14.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            symbol,
            color = Gold,
            fontSize = 24.sp
        )
    }
}

// PERSONA CARD

@Composable
fun PersonaSelectionCard(
    name: String,
    description: String,
    color: Color,
    image: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val borderColor by animateColorAsState(
        targetValue = if (selected) {
            color
        } else {
            color.copy(alpha = 0.35f)
        },
        label = "cardBorder"
    )

    Column(
        modifier = modifier
            .shadow(
                elevation = if (selected) 12.dp else 3.dp,
                shape = RoundedCornerShape(19.dp),
                ambientColor = color.copy(alpha = 0.4f),
                spotColor = color.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(19.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF171D2C),
                        Color(0xFF090D17)
                    )
                )
            )
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(19.dp)
            )
            .clickable { onClick() }
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(155.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(13.dp)),
                contentScale = ContentScale.Fit
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected) color else Background.copy(alpha = 0.8f)
                    )
                    .border(1.dp, color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (selected) "✓" else "",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            name,
            color = color,
            fontSize = 19.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(5.dp))

        Text(
            description,
            color = Muted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(color.copy(alpha = 0.12f))
                .border(
                    1.dp,
                    color.copy(alpha = 0.8f),
                    RoundedCornerShape(30.dp)
                )
                .padding(vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (selected) "SELECTED ✓" else "SELECT",
                color = color,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}


// NAME INPUT FIELD

@Composable
fun NameInputField(
    value: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = androidx.compose.ui.text.TextStyle(
            color = White,
            fontSize = 16.sp
        ),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Panel)
                    .border(
                        1.dp,
                        Gold.copy(alpha = 0.55f),
                        RoundedCornerShape(15.dp)
                    )
                    .padding(horizontal = 17.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "✦",
                    color = Gold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.width(12.dp))

                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            "Enter your name...",
                            color = Muted,
                            fontSize = 15.sp
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

// NAME PREVIEW

@Composable
fun NamePreviewCard(
    persona: String,
    userName: String
) {
    val accent = when (persona) {
        "ultron" -> Blue
        "luna" -> Purple
        else -> Gold
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(17.dp))
            .background(Panel)
            .border(
                1.dp,
                accent.copy(alpha = 0.45f),
                RoundedCornerShape(17.dp)
            )
            .padding(17.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "NAME PREVIEW",
            color = Muted,
            fontSize = 10.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = if (userName.isBlank()) {
                "Hello, User!"
            } else {
                "Hello, ${userName.trim()}!"
            },
            color = White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = when (persona) {
                "ultron" -> "ULTRON is ready to assist you."
                "luna" -> "LUNA is here for you."
                else -> "Your assistant is waiting."
            },
            color = accent,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

// SAVE AND CONTINUE BUTTON

@Composable
fun SaveContinueButton(
    enabled: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .shadow(
                elevation = if (enabled) 8.dp else 0.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = color,
                spotColor = color
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Background,
            disabledContainerColor = Color(0xFF303442),
            disabledContentColor = Muted
        )
    ) {
        Text(
            "SAVE & CONTINUE   →",
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )
    }
}

// FOOTER

@Composable
fun FooterSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "TOGETHER, ALWAYS...",
            color = Gold,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "One Assistant. Two Personalities. Endless Possibilities.",
            color = Muted,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(5.dp))

        Text(
            "ULTRON x LUNA",
            color = Muted.copy(alpha = 0.65f),
            fontSize = 9.sp,
            letterSpacing = 2.sp
        )
    }
}

// HOME SCREEN

@Composable
fun HomeScreen(
    persona: String,
    userName: String,
    onSettings: () -> Unit
) {
    val isUltron = persona == "ultron"
    val accent = if (isUltron) Blue else Purple
    val assistantName = if (isUltron) "ULTRON" else "LUNA"
    val characterImage = if (isUltron) {
        R.drawable.ultron_character
    } else {
        R.drawable.luna_character
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
            .padding(horizontal = 18.dp)
    ) {

        // HOME HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 17.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Personal AI Assistant",
                    color = White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    assistantName,
                    color = accent,
                    fontSize = 13.sp,
                    letterSpacing = 4.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            HeaderIcon(
                symbol = "⚙",
                onClick = onSettings
            )
        }

        Spacer(Modifier.weight(1f))

        // CHARACTER

        Image(
            painter = painterResource(characterImage),
            contentDescription = assistantName,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "Hello${if (userName.isNotBlank()) ", $userName" else ""}!",
            color = White,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            if (isUltron) {
                "Systems online. How can I assist you?"
            } else {
                "Hello! I'm here for you."
            },
            color = Muted,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(35.dp))

        Text(
            "AI CONNECTION COMING SOON",
            color = accent,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        // MESSAGE INPUT PREVIEW

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(35.dp))
                .background(Panel)
                .border(
                    1.dp,
                    accent.copy(alpha = 0.65f),
                    RoundedCornerShape(35.dp)
                )
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(1f)) {
                if (message.isEmpty()) {
                    Text(
                        "Message $assistantName...",
                        color = Muted,
                        fontSize = 15.sp
                    )
                }

                BasicTextField(
                    value = message,
                    onValueChange = { message = it },
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = White,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                "➤",
                color = accent,
                fontSize = 27.sp,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "AI responses will be enabled in the next development stage.",
            color = Muted,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
    }
}

// SETTINGS SCREEN

@Composable
fun SettingsScreen(
    persona: String,
    userName: String,
    onNameChange: (String) -> Unit,
    onPersonaChange: (String) -> Unit,
    onBack: () -> Unit
) {
    var editedName by rememberSaveable(userName) {
        mutableStateOf(userName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // SETTINGS HEADER

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderIcon(
                symbol = "←",
                onClick = onBack
            )

            Spacer(Modifier.width(15.dp))

            Text(
                "Settings",
                color = White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(35.dp))

        Text(
            "YOUR PROFILE",
            color = Gold,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "Your Name",
            color = White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        NameInputField(
            value = editedName,
            onValueChange = { editedName = it }
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                onNameChange(editedName.trim())
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gold,
                contentColor = Background
            ),
            shape = RoundedCornerShape(13.dp)
        ) {
            Text(
                "SAVE NAME",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(35.dp))

        Text(
            "CHANGE ASSISTANT",
            color = Gold,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(15.dp))

        SettingsPersonaOption(
            name = "ULTRON",
            description = "Professional • Intelligent • Powerful",
            color = Blue,
            selected = persona == "ultron",
            onClick = {
                onPersonaChange("ultron")
            }
        )

        Spacer(Modifier.height(15.dp))

        SettingsPersonaOption(
            name = "LUNA",
            description = "Friendly • Caring • Intelligent",
            color = Purple,
            selected = persona == "luna",
            onClick = {
                onPersonaChange("luna")
            }
        )

        Spacer(Modifier.height(35.dp))

        Text(
            "ULTRON x LUNA",
            color = Gold,
            fontSize = 12.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Personal AI Assistant",
            color = Muted,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

// SETTINGS PERSONA OPTION

@Composable
fun SettingsPersonaOption(
    name: String,
    description: String,
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Panel)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = color.copy(
                    alpha = if (selected) 1f else 0.35f
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                name,
                color = color,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(5.dp))

            Text(
                description,
                color = Muted,
                fontSize = 11.sp
            )
        }

        Text(
            if (selected) "✓" else "○",
            color = color,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
