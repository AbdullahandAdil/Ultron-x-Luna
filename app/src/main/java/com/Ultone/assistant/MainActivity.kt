package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import kotlinx.coroutines.delay

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

// --------------------------------------------------
// MAIN APP
// --------------------------------------------------

@Composable
fun AssistantApp() {

    var page by remember {
        mutableStateOf("welcome")
    }

    var assistant by remember {
        mutableStateOf("ULTRON")
    }

    var userName by remember {
        mutableStateOf("")
    }

    var appearance by remember {
        mutableStateOf("Character")
    }

    when (page) {

        "welcome" -> WelcomeScreen(
            onSelect = {
                assistant = it
                appearance = "Character"
                page = "customize"
            }
        )

        "customize" -> CustomizeScreen(
            assistant = assistant,
            initialName = userName,
            initialAppearance = appearance,
            onBack = {
                page = "welcome"
            },
            onContinue = { name, selected ->
                userName = name
                appearance = "Character"
                page = "home"
            }
        )

        "home" -> HomeScreen(
            assistant = assistant,
            userName = userName,
            appearance = "Character",
            onBack = {
                page = "welcome"
            }
        )
    }
}

// --------------------------------------------------
// BACKGROUND
// --------------------------------------------------

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

        Canvas(
            Modifier.fillMaxSize()
        ) {

            for (i in 0..65) {

                val x = (i * 83f) % size.width
                val y = (i * 137f) % size.height

                drawCircle(
                    color = White.copy(alpha = 0.28f),
                    radius = 1.2f,
                    center = Offset(x, y)
                )
            }
        }
    }
}

// --------------------------------------------------
// HEADER
// --------------------------------------------------

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
                modifier = Modifier.clickable {
                    onBack()
                }
            )

        } else {

            Text(
                "☰",
                color = color,
                fontSize = 27.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    R.drawable.app_logo
                ),
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

        Text(
            "⚙",
            color = color,
            fontSize = 27.sp
        )
    }
}

// --------------------------------------------------
// WELCOME SCREEN
// --------------------------------------------------

@Composable
fun WelcomeScreen(
    onSelect: (String) -> Unit
) {

    Box(
        Modifier.fillMaxSize()
    ) {

        AppBackground()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(Blue)

            Spacer(
                Modifier.height(25.dp)
            )

            Text(
                "CHOOSE YOUR ASSISTANT",
                color = White,
                fontSize = 19.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                Modifier.height(25.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(500.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
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

            Spacer(
                Modifier.height(45.dp)
            )

            Text(
                "✦  TOGETHER, ALWAYS...  ✦",
                color = White,
                fontSize = 15.sp,
                letterSpacing = 2.sp
            )

            Spacer(
                Modifier.height(25.dp)
            )
        }
    }
}

// --------------------------------------------------
// ASSISTANT CARD WITH ANIMATION
// --------------------------------------------------

@Composable
fun AssistantCard(
    name: String,
    tagline: String,
    color: Color,
    image: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(if (name == "ULTRON") 100 else 250)
        visible = true
    }

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "card_glow"
        )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    val interactionSource =
        remember { MutableInteractionSource() }

    val pressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "card_scale"
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(650)
        ) + slideInVertically(
            initialOffsetY = { it / 8 },
            animationSpec = tween(650)
        ),
        modifier = modifier
    ) {

        Column(
            Modifier
                .fillMaxHeight()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clip(RoundedCornerShape(25.dp))
                .background(
                    color.copy(alpha = 0.13f)
                )
                .border(
                    width = 2.dp,
                    color = color.copy(alpha = glowAlpha),
                    shape = RoundedCornerShape(25.dp)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onClick()
                }
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

                Text(
                    "Select  ›",
                    color = White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

// --------------------------------------------------
// CHARACTER PREVIEW WITH FLOATING ANIMATION
// --------------------------------------------------

@Composable
fun CharacterPreview(
    assistant: String,
    modifier: Modifier = Modifier
) {

    val color =
        if (assistant == "ULTRON") Blue else Purple

    val image =
        if (assistant == "ULTRON") {
            R.drawable.ultron_character
        } else {
            R.drawable.luna_character
        }

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "character_float"
        )

    val floatY by infiniteTransition.animateFloat(
        initialValue = -7f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_y"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Box(
            Modifier
                .fillMaxWidth(0.82f)
                .fillMaxHeight(0.88f)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            color.copy(alpha = 0.20f),
                            Dark.copy(alpha = 0.3f)
                        )
                    )
                )
                .border(
                    1.5.dp,
                    color.copy(alpha = 0.65f),
                    RoundedCornerShape(28.dp)
                )
        )

        Image(
            painter = painterResource(image),
            contentDescription = assistant,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.95f)
                .graphicsLayer {
                    translationY = floatY
                },
            contentScale = ContentScale.Fit
        )
    }
}

// --------------------------------------------------
// CUSTOMIZE SCREEN
// --------------------------------------------------

@Composable
fun CustomizeScreen(
    assistant: String,
    initialName: String,
    initialAppearance: String,
    onBack: () -> Unit,
    onContinue: (String, String) -> Unit
) {

    val color =
        if (assistant == "ULTRON") Blue else Purple

    var userName by remember {
        mutableStateOf(initialName)
    }

    var appearance by remember {
        mutableStateOf("Character")
    }

    val greetingName =
        userName.trim().ifEmpty {
            "User"
        }

    var screenVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        screenVisible = true
    }

    BoxWithConstraints(
        Modifier.fillMaxSize()
    ) {

        val compact = maxHeight < 750.dp

        AppBackground()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                color = color,
                onBack = onBack
            )

            Spacer(
                Modifier.height(
                    if (compact) 8.dp else 18.dp
                )
            )

            AnimatedVisibility(
                visible = screenVisible,
                enter = fadeIn(tween(600)) +
                    slideInVertically(
                        initialOffsetY = { -30 },
                        animationSpec = tween(600)
                    )
            ) {

                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        "CUSTOMIZE YOUR ASSISTANT",
                        color = White,
                        fontSize = if (compact) 17.sp else 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        Modifier.height(6.dp)
                    )

                    Text(
                        "Make your AI companion personal",
                        color = White.copy(alpha = 0.65f),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(
                Modifier.height(
                    if (compact) 12.dp else 20.dp
                )
            )

            // CHARACTER PREVIEW

            CharacterPreview(
                assistant = assistant,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (compact) 205.dp else 270.dp
                    )
            )

            Spacer(
                Modifier.height(12.dp)
            )

            // CHARACTER SELECTION CARD

            AppearanceOption(
                title = "CHARACTER",
                subtitle = "Your AI Companion",
                color = color,
                selected = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                Modifier.height(16.dp)
            )

            // NAME INPUT HEADING

            Text(
                "WHAT SHOULD I CALL YOU?",
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                Modifier.height(8.dp)
            )

            // NAME INPUT

            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Enter your name",
                        color = White.copy(alpha = 0.45f)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    focusedBorderColor = color,
                    unfocusedBorderColor =
                        color.copy(alpha = 0.45f),
                    cursorColor = color,
                    focusedContainerColor =
                        Color(0xFF10172A),
                    unfocusedContainerColor =
                        Color(0xFF10172A)
                )
            )

            Spacer(
                Modifier.height(12.dp)
            )

            // LIVE GREETING PREVIEW

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color.copy(alpha = 0.10f)
                    )
                    .border(
                        1.dp,
                        color.copy(alpha = 0.45f),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(14.dp)
            ) {

                Text(
                    "GREETING PREVIEW",
                    color = color,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(
                    Modifier.height(6.dp)
                )

                Text(
                    if (assistant == "ULTRON") {
                        "Hello, $greetingName. ULTRON is ready to assist you."
                    } else {
                        "Hey, $greetingName! Luna is always here for you."
                    },
                    color = White,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }

            Spacer(
                Modifier.height(18.dp)
            )

            // SAVE AND CONTINUE BUTTON

            Button(
                onClick = {
                    onContinue(
                        userName.trim(),
                        appearance
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color.copy(alpha = 0.22f)
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    "SAVE & CONTINUE  ›",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(
                Modifier.height(18.dp)
            )

            Text(
                "✦  YOUR AI. YOUR WORLD.  ✦",
                color = White.copy(alpha = 0.55f),
                fontSize = 11.sp,
                letterSpacing = 1.5.sp
            )

            Spacer(
                Modifier.height(20.dp)
            )
        }
    }
}

// --------------------------------------------------
// CHARACTER OPTION CARD
// --------------------------------------------------

@Composable
fun AppearanceOption(
    title: String,
    subtitle: String,
    color: Color,
    selected: Boolean,
    modifier: Modifier = Modifier
) {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "option_glow"
        )

    val glow by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "option_alpha"
    )

    Row(
        modifier
            .clip(RoundedCornerShape(18.dp))
            .background(
                color.copy(alpha = 0.12f)
            )
            .border(
                width = 1.5.dp,
                color = color.copy(
                    alpha = if (selected) glow else 0.3f
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(15.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    color.copy(alpha = 0.18f)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                "✦",
                color = color,
                fontSize = 25.sp
            )
        }

        Spacer(
            Modifier.width(12.dp)
        )

        Column(
            Modifier.weight(1f)
        ) {

            Text(
                title,
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                subtitle,
                color = White.copy(alpha = 0.65f),
                fontSize = 11.sp
            )
        }

        Text(
            "✓ SELECTED",
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --------------------------------------------------
// HOME SCREEN
// --------------------------------------------------

@Composable
fun HomeScreen(
    assistant: String,
    userName: String,
    appearance: String,
    onBack: () -> Unit
) {

    val color =
        if (assistant == "ULTRON") Blue else Purple

    val greetingName =
        userName.ifBlank {
            "User"
        }

    Box(
        Modifier.fillMaxSize()
    ) {

        AppBackground()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                color = color,
                onBack = onBack
            )

            Spacer(
                Modifier.height(20.dp)
            )

            Text(
                "YOUR ASSISTANT IS READY",
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                Modifier.height(15.dp)
            )

            CharacterPreview(
                assistant = assistant,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
            )

            Spacer(
                Modifier.height(18.dp)
            )

            Text(
                assistant,
                color = color,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 3.sp
            )

            Spacer(
                Modifier.height(10.dp)
            )

            Text(
                if (assistant == "ULTRON") {
                    "Hello, $greetingName. I am ready to assist you."
                } else {
                    "Hey, $greetingName! I'm Luna. How can I help you today?"
                },
                color = White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(
                Modifier.height(25.dp)
            )

            // CHAT PLACEHOLDER

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        color.copy(alpha = 0.10f)
                    )
                    .border(
                        1.dp,
                        color.copy(alpha = 0.4f),
                        RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    "✦",
                    color = color,
                    fontSize = 35.sp
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Text(
                    "AI CHAT",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    Modifier.height(5.dp)
                )

                Text(
                    "Your conversation space is coming soon.",
                    color = White.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                Modifier.height(25.dp)
            )

            Text(
                "TOGETHER, ALWAYS...",
                color = White.copy(alpha = 0.55f),
                fontSize = 12.sp,
                letterSpacing = 2.sp
            )

            Spacer(
                Modifier.height(25.dp)
            )
        }
    }
}

// --------------------------------------------------
// END OF MAIN ACTIVITY
// --------------------------------------------------