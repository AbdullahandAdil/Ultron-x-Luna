
package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val AppBlack = Color(0xFF05070D)
private val PanelColor = Color(0xFF101725)
private val PanelLight = Color(0xFF172235)
private val NeonBlue = Color(0xFF00C8FF)
private val NeonPurple = Color(0xFFB45CFF)
private val Gold = Color(0xFFFFD36A)
private val SoftWhite = Color(0xFFEAF2FF)
private val MutedText = Color(0xFF8D9AAF)

enum class AppPage {
    SPLASH, CUSTOMIZE, HOME, SETTINGS
}

enum class Persona {
    ULTRON, LUNA
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = android.graphics.Color.rgb(5, 7, 13)
        window.navigationBarColor = android.graphics.Color.rgb(5, 7, 13)

        setContent {
            UltronLunaApp()
        }
    }
}

@Composable
fun UltronLunaApp() {
    var currentPage by remember { mutableStateOf(AppPage.SPLASH) }
    var selectedPersona by remember { mutableStateOf(Persona.ULTRON) }
    var userName by remember { mutableStateOf("Boss") }
    var setupComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        currentPage = if (setupComplete) AppPage.HOME else AppPage.CUSTOMIZE
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = AppBlack,
            surface = PanelColor,
            primary = NeonBlue,
            secondary = NeonPurple
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppBlack
        ) {
            when (currentPage) {
                AppPage.SPLASH -> SplashScreen()

                AppPage.CUSTOMIZE -> CustomizeScreen(
                    selectedPersona = selectedPersona,
                    onPersonaSelected = { selectedPersona = it },
                    userName = userName,
                    onNameChanged = { userName = it },
                    onContinue = {
                        setupComplete = true
                        currentPage = AppPage.HOME
                    },
                    onSettings = {
                        currentPage = AppPage.SETTINGS
                    }
                )

                AppPage.HOME -> HomeScreen(
                    persona = selectedPersona,
                    userName = userName,
                    onSettings = {
                        currentPage = AppPage.SETTINGS
                    },
                    onMenu = {
                        currentPage = AppPage.CUSTOMIZE
                    }
                )

                AppPage.SETTINGS -> SettingsScreen(
                    persona = selectedPersona,
                    onBack = {
                        currentPage = AppPage.HOME
                    },
                    onPersonaChanged = {
                        selectedPersona = it
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF080D18),
                        AppBlack,
                        Color(0xFF090613)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        val pulse = rememberInfiniteTransition(label = "logoPulse")
        val glow by pulse.animateFloat(
            initialValue = 0.75f,
            targetValue = 1.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(1300, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glow"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageLogo(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer {
                        scaleX = glow
                        scaleY = glow
                    }
            )

            Spacer(Modifier.height(26.dp))

            Text(
                text = "ULTRON  X  LUNA",
                color = Gold,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(Modifier.height(9.dp))

            Text(
                text = "PERSONAL AI ASSISTANT",
                color = MutedText,
                fontSize = 11.sp,
                letterSpacing = 3.sp
            )
        }
    }
}

@Composable
fun ImageLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "Ultron x Luna Crown Logo",
        modifier = modifier,
        contentScale = androidx.compose.ui.layout.ContentScale.Fit
    )
}

@Composable
fun CustomizeScreen(
    selectedPersona: Persona,
    onPersonaSelected: (Persona) -> Unit,
    userName: String,
    onNameChanged: (String) -> Unit,
    onContinue: () -> Unit,
    onSettings: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF080E1B),
                        AppBlack,
                        Color(0xFF0B0818)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp)
                .padding(bottom = 22.dp)
        ) {
            CustomizeHeader(
                onMenu = {},
                onSettings = onSettings
            )

            Spacer(Modifier.height(25.dp))

            Text(
                text = "CHOOSE YOUR PERSONALITY",
                color = SoftWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(7.dp))

            Text(
                text = "One assistant. Two unique personalities.",
                color = MutedText,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PersonaCard(
                    persona = Persona.ULTRON,
                    selected = selectedPersona == Persona.ULTRON,
                    modifier = Modifier.weight(1f),
                    onSelect = { onPersonaSelected(Persona.ULTRON) }
                )

                PersonaCard(
                    persona = Persona.LUNA,
                    selected = selectedPersona == Persona.LUNA,
                    modifier = Modifier.weight(1f),
                    onSelect = { onPersonaSelected(Persona.LUNA) }
                )
            }

            Spacer(Modifier.height(25.dp))

            GreetingPreview(
                userName = userName
            )

            Spacer(Modifier.height(18.dp))

            NameInputSection(
                userName = userName,
                onNameChanged = onNameChanged
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = Gold.copy(alpha = 0.25f),
                        spotColor = Gold.copy(alpha = 0.35f)
                    ),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Color(0xFF17120A)
                )
            ) {
                Text(
                    text = "SAVE & CONTINUE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(Modifier.height(22.dp))

            Text(
                text = "TOGETHER, ALWAYS...",
                color = Gold.copy(alpha = 0.9f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "One Assistant. Two Personalities. Endless Possibilities.",
                color = MutedText,
                fontSize = 9.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CustomizeHeader(
    onMenu: () -> Unit,
    onSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = SoftWhite
                )
            }

            Spacer(Modifier.weight(1f))

            ImageLogo(
                modifier = Modifier.size(55.dp)
            )

            Spacer(Modifier.weight(1f))

            IconButton(onClick = onSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Gold
                )
            }
        }

        Spacer(Modifier.height(5.dp))

        Text(
            text = "PERSONAL AI ASSISTANT",
            color = Gold,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
    }
}

@Composable
fun PersonaCard(
    persona: Persona,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit
) {
    val isUltron = persona == Persona.ULTRON
    val accent = if (isUltron) NeonBlue else NeonPurple

    val animatedBorder by animateColorAsState(
        targetValue = if (selected) accent else Color(0xFF263247),
        animationSpec = tween(350),
        label = "borderColor"
    )

    val infinite = rememberInfiniteTransition(
        label = "cardMotion${persona.name}"
    )

    val floatY by infinite.animateFloat(
        initialValue = 0f,
        targetValue = if (selected) -5f else -2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY"
    )

    Column(
        modifier = modifier
            .graphicsLayer {
                translationY = floatY
            }
            .shadow(
                elevation = if (selected) 20.dp else 7.dp,
                shape = RoundedCornerShape(22.dp),
                ambientColor = accent.copy(
                    alpha = if (selected) 0.35f else 0.08f
                ),
                spotColor = accent.copy(
                    alpha = if (selected) 0.45f else 0.08f
                )
            )
            .border(
                BorderStroke(
                    width = if (selected) 2.dp else 1.dp,
                    color = animatedBorder
                ),
                RoundedCornerShape(22.dp)
            )
            .background(
                Brush.verticalGradient(
                    listOf(
                        if (selected) accent.copy(alpha = 0.13f)
                        else PanelLight,
                        PanelColor,
                        Color(0xFF090E18)
                    )
                ),
                RoundedCornerShape(22.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(155.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isUltron) {
                Image(
                    painter = painterResource(
                        id = R.drawable.ultron_character
                    ),
                    contentDescription = "ULTRON",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(
                        id = R.drawable.luna_character
                    ),
                    contentDescription = "LUNA",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            if (selected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(7.dp)
                        .size(27.dp)
                        .shadow(8.dp, CircleShape)
                        .background(accent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = AppBlack,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(13.dp))

        Text(
            text = if (isUltron) "ULTRON" else "LUNA",
            color = accent,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = if (isUltron) {
                "Professional • Intelligent • Powerful"
            } else {
                "Caring • Intelligent • Friendly"
            },
            color = SoftWhite,
            fontSize = 9.sp,
            lineHeight = 14.sp,
            minLines = 2,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(13.dp))

        Button(
            onClick = onSelect,
            modifier = Modifier
                .fillMaxWidth()
                .height(39.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) accent else Color(0xFF263247),
                contentColor = if (selected) AppBlack else SoftWhite
            ),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Text(
                text = if (selected) "✓ SELECTED" else "SELECT",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun GreetingPreview(
    userName: String
) {
    var currentTime by remember {
        mutableStateOf(LocalTime.now())
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(30_000)
        }
    }

    val hour = currentTime.hour

    val greeting = when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    val icon = when (hour) {
        in 5..11 -> "☀"
        in 12..16 -> "☀"
        in 17..20 -> "🌇"
        else -> "🌙"
    }

    val infinite = rememberInfiniteTransition(
        label = "greetingGlow"
    )

    val glowAlpha by infinite.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "greetingAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = NeonBlue.copy(alpha = 0.15f)
            )
            .border(
                1.dp,
                Brush.horizontalGradient(
                    listOf(
                        NeonBlue.copy(alpha = glowAlpha),
                        NeonPurple.copy(alpha = glowAlpha),
                        Gold.copy(alpha = glowAlpha)
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF101D30),
                        Color(0xFF171326)
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GREETING PREVIEW",
            color = MutedText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(13.dp))

        Text(
            text = icon,
            fontSize = 28.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "$greeting, ${userName.ifBlank { "Boss" }}!",
            color = Gold,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "Your assistant is ready for you.",
            color = MutedText,
            fontSize = 11.sp
        )
    }
}

@Composable
fun NameInputSection(
    userName: String,
    onNameChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "WHAT SHOULD I CALL YOU?",
            color = SoftWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        Spacer(Modifier.height(9.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = {
                onNameChanged(it.take(24))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter your name or nickname",
                    color = MutedText
                )
            },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = SoftWhite,
                unfocusedTextColor = SoftWhite,
                focusedBorderColor = NeonBlue,
                unfocusedBorderColor = Color(0xFF344258),
                cursorColor = NeonBlue,
                focusedContainerColor = PanelColor,
                unfocusedContainerColor = PanelColor
            )
        )
    }
}

@Composable
fun HomeScreen(
    persona: Persona,
    userName: String,
    onSettings: () -> Unit,
    onMenu: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val gifLoader = remember {
        coil.ImageLoader.Builder(context)
            .components {
                add(coil.decode.GifDecoder.Factory())
            }
            .build()
    }

    val gifResource = if (persona == Persona.ULTRON) {
        R.drawable.ultron_character_anim
    } else {
        R.drawable.luna_character_anim
    }

    val accent = if (persona == Persona.ULTRON) {
        NeonBlue
    } else {
        NeonPurple
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF080E1B),
                        AppBlack,
                        Color(0xFF090613)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onMenu) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = SoftWhite
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = "ULTRON  X  LUNA",
                    color = Gold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Gold
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Hello, ${userName.ifBlank { "Boss" }}!",
                color = SoftWhite,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = if (persona == Persona.ULTRON) {
                    "ULTRON is online. At your service."
                } else {
                    "LUNA is here. Always by your side."
                },
                color = MutedText,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(270.dp)
                        .background(
                            accent.copy(alpha = 0.07f),
                            CircleShape
                        )
                        .border(
                            1.dp,
                            accent.copy(alpha = 0.22f),
                            CircleShape
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    coil.compose.AsyncImage(
                        model = coil.request.ImageRequest.Builder(context)
                            .data(gifResource)
                            .crossfade(false)
                            .build(),
                        imageLoader = gifLoader,
                        contentDescription = if (
                            persona == Persona.ULTRON
                        ) "ULTRON animated character" else "LUNA animated character",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }
            }

            Text(
                text = if (persona == Persona.ULTRON) "ULTRON" else "LUNA",
                color = accent,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 4.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Your personal AI assistant",
                color = MutedText,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent,
                    contentColor = AppBlack
                )
            ) {
                Text(
                    text = "TAP TO TALK",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun SettingsScreen(
    persona: Persona,
    onBack: () -> Unit,
    onPersonaChanged: (Persona) -> Unit
) {
    var voiceEnabled by remember {
        mutableStateOf(true)
    }

    var selectedLanguage by remember {
        mutableStateOf("Auto Detect")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBlack)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text(
                    text = "‹  BACK",
                    color = NeonBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "SETTINGS",
                color = Gold,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.weight(1f))

            Spacer(Modifier.width(48.dp))
        }

        Spacer(Modifier.height(25.dp))

        SettingsHeading("ACTIVE PERSONALITY")

        SettingsOption(
            title = "ULTRON",
            subtitle = "Professional • Deep Voice",
            selected = persona == Persona.ULTRON,
            accent = NeonBlue,
            onClick = {
                onPersonaChanged(Persona.ULTRON)
            }
        )

        Spacer(Modifier.height(10.dp))

        SettingsOption(
            title = "LUNA",
            subtitle = "Friendly • Soft Voice",
            selected = persona == Persona.LUNA,
            accent = NeonPurple,
            onClick = {
                onPersonaChanged(Persona.LUNA)
            }
        )

        Spacer(Modifier.height(28.dp))

        SettingsHeading("VOICE PREFERENCES")

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = PanelColor
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Voice Responses",
                        color = SoftWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Enable spoken responses",
                        color = MutedText,
                        fontSize = 11.sp
                    )
                }

                Switch(
                    checked = voiceEnabled,
                    onCheckedChange = {
                        voiceEnabled = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppBlack,
                        checkedTrackColor = NeonBlue
                    )
                )
            }
        }

        Spacer(Modifier.height(25.dp))

        SettingsHeading("LANGUAGE")

        var languageExpanded by remember {
            mutableStateOf(false)
        }

        Box {
            OutlinedButton(
                onClick = {
                    languageExpanded = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(
                    1.dp,
                    Color(0xFF344258)
                )
            ) {
                Text(
                    text = selectedLanguage,
                    color = SoftWhite
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "⌄",
                    color = NeonBlue
                )
            }

            DropdownMenu(
                expanded = languageExpanded,
                onDismissRequest = {
                    languageExpanded = false
                },
                modifier = Modifier.background(PanelColor)
            ) {
                listOf(
                    "Auto Detect",
                    "English",
                    "Hindi",
                    "Urdu",
                    "Arabic",
                    "Multi Language"
                ).forEach { language ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = language,
                                color = SoftWhite
                            )
                        },
                        onClick = {
                            selectedLanguage = language
                            languageExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = "ULTRON X LUNA",
            color = Gold,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(7.dp))

        Text(
            text = "One Assistant. Two Personalities.",
            color = MutedText,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SettingsHeading(
    title: String
) {
    Text(
        text = title,
        color = Gold,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp
    )

    Spacer(Modifier.height(12.dp))
}

@Composable
fun SettingsOption(
    title: String,
    subtitle: String,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(PanelColor)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) accent else Color(0xFF263247),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = accent,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = subtitle,
                color = MutedText,
                fontSize = 10.sp
            )
        }

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = accent
            )
        }
    }
}
