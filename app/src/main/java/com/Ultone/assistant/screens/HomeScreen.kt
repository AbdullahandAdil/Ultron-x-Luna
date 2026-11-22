
package com.ultone.assistant.screens

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import com.ultone.assistant.AppPage
import com.ultone.assistant.R
import com.ultone.assistant.data.Persona
import com.ultone.assistant.components.BottomNavigationBar
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val HomeBackground = Color(0xFF080B14)
private val HomeCard = Color(0xFF111C2C)
private val HomeGold = Color(0xFFD9AE50)
private val HoloBlue = Color(0xFF35DFFF)

@Composable
fun HomeScreen(
    selectedPersona: Persona,
    userName: String,
    onNavigate: (AppPage) -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    val themeColor = when (selectedPersona) {
        Persona.ULTRON -> Color(0xFF24DFFF)
        Persona.LUNA -> Color(0xFFBD65FF)
    }

    var dateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    var batteryLevel by remember {
        mutableIntStateOf(readBattery(context))
    }

    var selectedCountry by remember { mutableStateOf("India") }
    var selectedState by remember { mutableStateOf("Uttar Pradesh") }
    var showCountryDialog by remember { mutableStateOf(false) }
    var showStateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            dateTime = LocalDateTime.now()
            batteryLevel = readBattery(context)
            delay(1000)
        }
    }

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(GifDecoder.Factory())
            }
            .build()
    }

    val characterImage = when (selectedPersona) {
        Persona.ULTRON -> R.drawable.ultron_character_anim
        Persona.LUNA -> R.drawable.luna_character_anim
    }

    val greeting = when (dateTime.hour) {
        in 5..11 -> "Good Morning, "
        in 12..16 -> "Good Afternoon, "
        in 17..20 -> "Good Evening, "
        else -> "Good Night, "
    }

    val dateText = dateTime.format(
        DateTimeFormatter.ofPattern(
            "EEE, dd MMM yyyy",
            Locale.getDefault()
        )
    )

    val timeText = dateTime.format(
        DateTimeFormatter.ofPattern(
            "hh:mm:ss a",
            Locale.getDefault()
        )
    )

    // Subtle character breathing animation
    val characterTransition = rememberInfiniteTransition(
        label = "characterBreathing"
    )

    val characterScale by characterTransition.animateFloat(
        initialValue = 0.985f,
        targetValue = 1.015f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "characterScale"
    )

    val glowAlpha by characterTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "characterGlow"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
    ) {

        // HEADER

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "☰",
                color = Color.White,
                fontSize = 29.sp,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = selectedPersona.name,
                color = HomeGold,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "⚙",
                    color = Color.White,
                    fontSize = 27.sp
                )
            }
        }

        // HEADER DIVIDER

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .height(1.dp)
                .background(Color(0xFF263448))
        )

        // MAIN CONTENT

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // GREETING - RIGHT SIDE

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.075f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(greeting)
                        }

                        withStyle(
                            SpanStyle(
                                color = themeColor,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(userName.ifBlank { "Boss" })
                            append("!")
                        }
                    },
                    fontSize = 19.sp,
                    maxLines = 1
                )
            }

            // CHARACTER CIRCLE

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.39f),
                contentAlignment = Alignment.Center
            ) {
                val characterSize = (maxHeight * 0.92f)
                    .coerceAtMost(235.dp)

                Box(
                    modifier = Modifier
                        .size(characterSize)
                        .scale(characterScale)
                        .shadow(
                            elevation = 24.dp,
                            shape = CircleShape,
                            ambientColor = themeColor.copy(alpha = glowAlpha),
                            spotColor = themeColor.copy(alpha = glowAlpha)
                        )
                        .border(
                            width = 2.dp,
                            color = themeColor.copy(alpha = glowAlpha),
                            shape = CircleShape
                        )
                        .padding(5.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = characterImage,
                        imageLoader = imageLoader,
                        contentDescription = "${selectedPersona.name} character",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            // CHARACTER NAME AND VOICE TEXT

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.13f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = selectedPersona.name,
                    color = HomeGold,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "I'm listening for my name...",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Say ${selectedPersona.name} to activate",
                    color = Color(0xFF8B9BB2),
                    fontSize = 11.sp
                )
            }

            // FOUR CONTAINERS - 2 x 2

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.40f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // FIRST ROW

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // DATE + TIME

                    HomeInfoCard(
                        modifier = Modifier.weight(1f),
                        title = "DATE",
                        value = dateText,
                        secondTitle = "TIME",
                        secondValue = timeText,
                        themeColor = themeColor
                    )

                    // WEATHER + TEMPERATURE

                    HomeInfoCard(
                        modifier = Modifier.weight(1f),
                        title = "WEATHER",
                        value = "Waiting for weather",
                        secondTitle = "TEMPERATURE",
                        secondValue = "-- °C  |  -- °F",
                        themeColor = themeColor
                    )
                }

                // SECOND ROW

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    // AI STATUS + BATTERY

                    HomeInfoCard(
                        modifier = Modifier.weight(1f),
                        title = "STATUS",
                        value = "Standby",
                        secondTitle = "BATTERY",
                        secondValue = "$batteryLevel%",
                        themeColor = themeColor
                    )

                    // WORLD MAP

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(20.dp))
                            .background(HomeCard)
                            .padding(9.dp)
                    ) {
                        Text(
                            text = "WORLD MAP",
                            color = themeColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        TextButton(
                            onClick = { showCountryDialog = true },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(27.dp)
                        ) {
                            Text(
                                text = "🌐 $selectedCountry",
                                color = Color.White,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }

                        TextButton(
                            onClick = { showStateDialog = true },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(25.dp)
                        ) {
                            Text(
                                text = "⌄ $selectedState",
                                color = Color(0xFFB8C8DC),
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }

                        HolographicWorldMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            themeColor = themeColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
        }

        // BOTTOM NAVIGATION - UNCHANGED

        BottomNavigationBar(
            selectedPage = AppPage.HOME,
            onPageSelected = onNavigate
        )
    }

    // COUNTRY SELECTOR

    if (showCountryDialog) {
        val countries = listOf(
            "India",
            "United States",
            "United Kingdom",
            "United Arab Emirates",
            "Saudi Arabia",
            "Pakistan",
            "Bangladesh",
            "Canada",
            "Australia",
            "Japan",
            "Other"
        )

        AlertDialog(
            onDismissRequest = { showCountryDialog = false },
            title = { Text("Select Country") },
            text = {
                Column {
                    countries.forEach { country ->
                        TextButton(
                            onClick = {
                                selectedCountry = country
                                selectedState = "Select state"
                                showCountryDialog = false
                            }
                        ) {
                            Text(country, color = Color.White)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showCountryDialog = false }
                ) {
                    Text("Close")
                }
            },
            containerColor = HomeCard,
            titleContentColor = HoloBlue,
            textContentColor = Color.White
        )
    }

    // STATE SELECTOR

    if (showStateDialog) {
        val states = if (selectedCountry == "India") {
            listOf(
                "Uttar Pradesh",
                "Delhi",
                "Bihar",
                "Maharashtra",
                "Madhya Pradesh",
                "Rajasthan",
                "West Bengal",
                "Gujarat",
                "Punjab",
                "Haryana",
                "Other"
            )
        } else {
            listOf(
                "Select state / region",
                "Other"
            )
        }

        AlertDialog(
            onDismissRequest = { showStateDialog = false },
            title = { Text("Select State / Region") },
            text = {
                Column {
                    states.forEach { state ->
                        TextButton(
                            onClick = {
                                selectedState = state
                                showStateDialog = false
                            }
                        ) {
                            Text(state, color = Color.White)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showStateDialog = false }
                ) {
                    Text("Close")
                }
            },
            containerColor = HomeCard,
            titleContentColor = HoloBlue,
            textContentColor = Color.White
        )
    }
}

// INFORMATION CARD

@Composable
private fun HomeInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    secondTitle: String,
    secondValue: String,
    themeColor: Color
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(HomeCard)
            .padding(horizontal = 7.dp, vertical = 9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = title,
            color = themeColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            maxLines = 1
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(1.dp)
                .background(Color(0xFF293B50))
        )

        Text(
            text = secondTitle,
            color = themeColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            maxLines = 1
        )

        Text(
            text = secondValue,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

// PHONE BATTERY

private fun readBattery(context: Context): Int {
    return try {
        val batteryManager = context.getSystemService(
            Context.BATTERY_SERVICE
        ) as BatteryManager

        val level = batteryManager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        )

        if (level in 0..100) level else {
            val intent = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            val current = intent?.getIntExtra(
                BatteryManager.EXTRA_LEVEL,
                -1
            ) ?: -1
            val scale = intent?.getIntExtra(
                BatteryManager.EXTRA_SCALE,
                -1
            ) ?: -1

            if (current >= 0 && scale > 0) {
                (current * 100) / scale
            } else {
                0
            }
        }
    } catch (e: Exception) {
        0
    }
}

// HOLOGRAPHIC WORLD MAP

@Composable
private fun HolographicWorldMap(
    modifier: Modifier = Modifier,
    themeColor: Color
) {
    val transition = rememberInfiniteTransition(
        label = "worldMapAnimation"
    )

    val pulse by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mapPulse"
    )

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0A1728))
    ) {
        val w = size.width
        val h = size.height

        // MAP GRID

        for (i in 1..4) {
            val y = h * i / 5f
            drawLine(
                color = themeColor.copy(alpha = 0.12f),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1f
            )
        }

        for (i in 1..7) {
            val x = w * i / 8f
            drawLine(
                color = themeColor.copy(alpha = 0.12f),
                start = Offset(x, 0f),
                end = Offset(x, h),
                strokeWidth = 1f
            )
        }

        // CONTINENT OUTLINES

        val continents = listOf(
            listOf(
                0.08f to 0.25f,
                0.22f to 0.10f,
                0.32f to 0.25f,
                0.29f to 0.48f,
                0.20f to 0.76f,
                0.11f to 0.62f
            ),
            listOf(
                0.30f to 0.62f,
                0.38f to 0.68f,
                0.40f to 0.92f,
                0.34f to 0.80f
            ),
            listOf(
                0.43f to 0.20f,
                0.54f to 0.12f,
                0.66f to 0.27f,
                0.59f to 0.43f,
                0.49f to 0.42f
            ),
            listOf(
                0.51f to 0.48f,
                0.63f to 0.47f,
                0.67f to 0.72f,
                0.60f to 0.91f,
                0.54f to 0.76f
            ),
            listOf(
                0.68f to 0.18f,
                0.80f to 0.10f,
                0.95f to 0.27f,
                0.89f to 0.48f,
                0.77f to 0.38f
            )
        )

        continents.forEach { points ->
            val path = Path().apply {
                points.forEachIndexed { index, point ->
                    val x = point.first * w
                    val y = point.second * h

                    if (index == 0) moveTo(x, y)
                    else lineTo(x, y)
                }
                close()
            }

            drawPath(
                path = path,
                color = themeColor.copy(alpha = 0.85f),
                style = Stroke(width = 1.3f)
            )
        }

        // GLOWING MAP POINTS

        val points = listOf(
            0.20f to 0.30f,
            0.49f to 0.30f,
            0.58f to 0.60f,
            0.78f to 0.28f,
            0.88f to 0.42f
        )

        points.forEach { point ->
            val center = Offset(
                point.first * w,
                point.second * h
            )

            drawCircle(
                color = themeColor.copy(alpha = 0.15f),
                radius = 9.dp.toPx(),
                center = center
            )

            drawCircle(
                color = themeColor.copy(alpha = pulse),
                radius = 3.dp.toPx(),
                center = center
            )
        }
    }
}
