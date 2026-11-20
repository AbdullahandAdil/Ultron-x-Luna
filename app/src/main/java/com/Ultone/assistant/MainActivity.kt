
package com.ultone.assistant

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay

private val DarkBackground = Color(0xFF030713)
private val PanelBackground = Color(0xFF080D1B)
private val Gold = Color(0xFFFFD36A)
private val Blue = Color(0xFF00D9FF)
private val Purple = Color(0xFFD044FF)
private val White = Color.White
private val Muted = Color(0xFF9AA6BD)

private enum class AppPage {
    SPLASH, WELCOME, HOME, SETTINGS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltronLunaApp()
        }
    }
}

@Composable
private fun UltronLunaApp() {
    val context = LocalContext.current

    val prefs = remember {
        context.getSharedPreferences(
            "ultron_luna_prefs",
            Context.MODE_PRIVATE
        )
    }

    var page by remember {
        mutableStateOf(AppPage.SPLASH)
    }

    var selectedPersona by remember {
        mutableStateOf(
            prefs.getString("persona", "") ?: ""
        )
    }

    var userName by remember {
        mutableStateOf(
            prefs.getString("user_name", "") ?: ""
        )
    }

    var ultronVoice by remember {
        mutableStateOf(
            prefs.getString("ultron_voice", "Deep") ?: "Deep"
        )
    }

    var lunaVoice by remember {
        mutableStateOf(
            prefs.getString("luna_voice", "Soft") ?: "Soft"
        )
    }

    var language by remember {
        mutableStateOf(
            prefs.getString("language", "Auto") ?: "Auto"
        )
    }

    val gifLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(GifDecoder.Factory())
            }
            .build()
    }

    LaunchedEffect(Unit) {
        delay(1300)

        page = if (
            selectedPersona.isNotBlank() &&
            userName.isNotBlank()
        ) {
            AppPage.HOME
        } else {
            AppPage.WELCOME
        }
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Gold,
            background = DarkBackground,
            surface = PanelBackground,
            onPrimary = DarkBackground,
            onBackground = White,
            onSurface = White
        )
    ) {
        Box(
            modifier = Modifier
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
            StarBackground()

            when (page) {
                AppPage.SPLASH -> {
                    SplashScreen(gifLoader)
                }

                AppPage.WELCOME -> {
                    WelcomeScreen(
                        gifLoader = gifLoader,
                        userName = userName,
                        onNameChange = {
                            userName = it
                        },
                        selectedPersona = selectedPersona,
                        onPersonaSelect = {
                            selectedPersona = it
                        },
                        onContinue = {
                            if (
                                userName.isNotBlank() &&
                                selectedPersona.isNotBlank()
                            ) {
                                prefs.edit()
                                    .putString(
                                        "user_name",
                                        userName.trim()
                                    )
                                    .putString(
                                        "persona",
                                        selectedPersona
                                    )
                                    .apply()

                                page = AppPage.HOME
                            }
                        }
                    )
                }

                AppPage.HOME -> {
                    HomeScreen(
                        gifLoader = gifLoader,
                        userName = userName,
                        persona = selectedPersona,
                        onSettings = {
                            page = AppPage.SETTINGS
                        },
                        onSwitchPersona = {
                            page = AppPage.WELCOME
                        }
                    )
                }

                AppPage.SETTINGS -> {
                    SettingsScreen(
                        userName = userName,
                        persona = selectedPersona,
                        ultronVoice = ultronVoice,
                        lunaVoice = lunaVoice,
                        language = language,
                        onUltronVoiceChange = {
                            ultronVoice = it
                            prefs.edit()
                                .putString("ultron_voice", it)
                                .apply()
                        },
                        onLunaVoiceChange = {
                            lunaVoice = it
                            prefs.edit()
                                .putString("luna_voice", it)
                                .apply()
                        },
                        onLanguageChange = {
                            language = it
                            prefs.edit()
                                .putString("language", it)
                                .apply()
                        },
                        onNameChange = {
                            userName = it
                            prefs.edit()
                                .putString("user_name", it)
                                .apply()
                        },
                        onBack = {
                            page = AppPage.HOME
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StarBackground() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val stars = listOf(
            Offset(
                size.width * 0.10f,
                size.height * 0.12f
            ),
            Offset(
                size.width * 0.82f,
                size.height * 0.18f
            ),
            Offset(
                size.width * 0.56f,
                size.height * 0.32f
            ),
            Offset(
                size.width * 0.22f,
                size.height * 0.52f
            ),
            Offset(
                size.width * 0.91f,
                size.height * 0.63f
            ),
            Offset(
                size.width * 0.42f,
                size.height * 0.79f
            ),
            Offset(
                size.width * 0.73f,
                size.height * 0.91f
            )
        )

        stars.forEach {
            drawCircle(
                color = Color.White.copy(alpha = 0.32f),
                radius = 1.5.dp.toPx(),
                center = it
            )
        }
    }
}

@Composable
private fun GifImage(
    resource: Int,
    loader: ImageLoader,
    modifier: Modifier = Modifier,
    description: String = ""
) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(resource)
            .crossfade(false)
            .build(),
        imageLoader = loader
    )

    androidx.compose.foundation.Image(
        painter = painter,
        contentDescription = description,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun SplashScreen(
    loader: ImageLoader
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            GifImage(
                resource = R.drawable.app_logo_anim,
                loader = loader,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(22.dp))

        Text(
            text = "ULTRON × LUNA",
            color = Gold,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Text(
            text = "YOUR PERSONAL AI ASSISTANT",
            color = Muted,
            fontSize = 11.sp,
            letterSpacing = 2.sp
        )
    }
}

@Composable
private fun WelcomeScreen(
    gifLoader: ImageLoader,
    userName: String,
    onNameChange: (String) -> Unit,
    selectedPersona: String,
    onPersonaSelect: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 18.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppHeader(
            title = "ULTRON AI ASSISTANT",
            subtitle = "Personal AI Assistant",
            showBack = false,
            onBack = {}
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "CHOOSE YOUR ASSISTANT",
            color = White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Text(
            text = "Select one persona to continue",
            color = Muted,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PersonaCard(
                modifier = Modifier.weight(1f),
                name = "ULTRON",
                description = "Professional • Powerful",
                color = Blue,
                selected = selectedPersona == "ULTRON",
                image = R.drawable.ultron_character_anim,
                loader = gifLoader,
                onClick = {
                    onPersonaSelect("ULTRON")
                }
            )

            PersonaCard(
                modifier = Modifier.weight(1f),
                name = "LUNA",
                description = "Friendly • Caring",
                color = Purple,
                selected = selectedPersona == "LUNA",
                image = R.drawable.luna_character_anim,
                loader = gifLoader,
                onClick = {
                    onPersonaSelect("LUNA")
                }
            )
        }

        Spacer(Modifier.height(25.dp))

        Text(
            text = "WHAT SHOULD I CALL YOU?",
            color = Gold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(
                    "Enter your name",
                    color = Muted
                )
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gold,
                unfocusedBorderColor = Muted.copy(
                    alpha = 0.45f
                ),
                focusedTextColor = White,
                unfocusedTextColor = White,
                cursorColor = Gold
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = onContinue,
            enabled = userName.isNotBlank() &&
                selectedPersona.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gold,
                contentColor = DarkBackground,
                disabledContainerColor = Muted.copy(
                    alpha = 0.25f
                ),
                disabledContentColor = Muted
            )
        ) {
            Text(
                "SAVE & CONTINUE",
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Together, Always...",
            color = Muted,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PersonaCard(
    modifier: Modifier,
    name: String,
    description: String,
    color: Color,
    selected: Boolean,
    image: Int,
    loader: ImageLoader,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF080B16))
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) color
                else color.copy(alpha = 0.25f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            }
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            GifImage(
                resource = image,
                loader = loader,
                modifier = Modifier.fillMaxSize(),
                description = name
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = name,
            color = color,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp
        )

        Text(
            text = description,
            color = Muted,
            fontSize = 9.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = if (selected) "SELECTED ✓" else "SELECT",
            color = if (selected) color else White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AppHeader(
    title: String,
    subtitle: String,
    showBack: Boolean,
    onBack: () -> Unit,
    onSettings: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBack) {
            Text(
                text = "‹",
                color = White,
                fontSize = 34.sp,
                modifier = Modifier
                    .clickable {
                        onBack()
                    }
                    .padding(end = 12.dp)
            )
        } else {
            Spacer(Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Gold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = subtitle,
                color = Muted,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }

        if (onSettings != null) {
            Text(
                text = "⚙",
                color = Gold,
                fontSize = 23.sp,
                modifier = Modifier
                    .clickable {
                        onSettings()
                    }
                    .padding(start = 12.dp)
            )
        } else {
            Spacer(Modifier.width(8.dp))
        }
    }
}

@Composable
private fun HomeScreen(
    gifLoader: ImageLoader,
    userName: String,
    persona: String,
    onSettings: () -> Unit,
    onSwitchPersona: () -> Unit
) {
    val isUltron = persona == "ULTRON"

    val accent = if (isUltron) Blue else Purple

    val image = if (isUltron) {
    R.drawable.ultron_character_anim
} else {
    R.drawable.luna_character_anim
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            )
    ) {
        AppHeader(
            title = "ULTRON × LUNA",
            subtitle = "Your Personal AI Assistant",
            showBack = false,
            onBack = {},
            onSettings = onSettings
        )

        Spacer(Modifier.height(22.dp))

        Text(
            text = "Hello, $userName",
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Your assistant is ready.",
            color = Muted,
            fontSize = 13.sp
        )

        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(22.dp))
                .background(Color.Black)
                .border(
                    1.dp,
                    accent.copy(alpha = 0.45f),
                    RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            GifImage(
                resource = image,
                loader = gifLoader,
                modifier = Modifier.fillMaxSize(),
                description = persona
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Color.Black.copy(alpha = 0.72f)
                    )
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = persona,
                    color = accent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )

                Text(
                    text = if (isUltron) {
                        "At your service."
                    } else {
                        "I'm here for you."
                    },
                    color = White,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        OutlinedButton(
            onClick = onSwitchPersona,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = accent
            )
        ) {
            Text("SWITCH ASSISTANT")
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(PanelBackground)
                .border(
                    1.dp,
                    accent.copy(alpha = 0.35f),
                    RoundedCornerShape(16.dp)
                )
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "＋",
                color = accent,
                fontSize = 25.sp
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = "AI chat will be connected in the next stage",
                color = Muted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SettingsScreen(
    userName: String,
    persona: String,
    ultronVoice: String,
    lunaVoice: String,
    language: String,
    onUltronVoiceChange: (String) -> Unit,
    onLunaVoiceChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        AppHeader(
            title = "SETTINGS",
            subtitle = "Manage your assistant",
            showBack = true,
            onBack = onBack
        )

        Spacer(Modifier.height(24.dp))

        SettingsSectionTitle("PROFILE")

        SettingsPanel {
            Text(
                text = "Your name",
                color = White,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Gold,
                    unfocusedBorderColor = Muted.copy(
                        alpha = 0.4f
                    ),
                    focusedTextColor = White,
                    unfocusedTextColor = White
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Active assistant: $persona",
                color = Gold,
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.height(20.dp))

        SettingsSectionTitle("VOICE SETTINGS")

        SettingsPanel {
            Text(
                text = "ULTRON voice",
                color = Blue,
                fontWeight = FontWeight.Bold
            )

            VoiceChoice(
                selected = ultronVoice,
                options = listOf("Deep", "Normal"),
                onSelect = onUltronVoiceChange
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "LUNA voice",
                color = Purple,
                fontWeight = FontWeight.Bold
            )

            VoiceChoice(
                selected = lunaVoice,
                options = listOf("Soft", "Normal"),
                onSelect = onLunaVoiceChange
            )
        }

        Spacer(Modifier.height(20.dp))

        SettingsSectionTitle("LANGUAGE")

        SettingsPanel {
            Text(
                text = "Assistant language",
                color = White,
                fontWeight = FontWeight.SemiBold
            )

            VoiceChoice(
                selected = language,
                options = listOf(
                    "Auto",
                    "English",
                    "Hindi",
                    "Urdu"
                ),
                onSelect = onLanguageChange
            )
        }

        Spacer(Modifier.height(20.dp))

        SettingsSectionTitle("ABOUT")

        SettingsPanel {
            Text(
                text = "ULTRON × LUNA",
                color = Gold,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = "Version 1.0 • Personal AI Assistant",
                color = Muted,
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.height(25.dp))
    }
}

@Composable
private fun SettingsSectionTitle(
    title: String
) {
    Text(
        text = title,
        color = Gold,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp
    )

    Spacer(Modifier.height(9.dp))
}

@Composable
private fun SettingsPanel(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(17.dp))
            .background(PanelBackground)
            .border(
                1.dp,
                White.copy(alpha = 0.09f),
                RoundedCornerShape(17.dp)
            )
            .padding(15.dp),
        content = content
    )
}

@Composable
private fun VoiceChoice(
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    Spacer(Modifier.height(9.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            val active = selected == option

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (active) {
                            Gold.copy(alpha = 0.18f)
                        } else {
                            Color(0xFF10182A)
                        }
                    )
                    .border(
                        1.dp,
                        if (active) Gold else Muted.copy(
                            alpha = 0.25f
                        ),
                        RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onSelect(option)
                    }
                    .padding(vertical = 11.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (active) Gold else White,
                    fontSize = 12.sp,
                    fontWeight = if (active) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}
