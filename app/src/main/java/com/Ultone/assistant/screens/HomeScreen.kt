
package com.ultone.assistant.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import com.ultone.assistant.AppPage
import com.ultone.assistant.data.Persona
import com.ultone.assistant.R
import com.ultone.assistant.components.BottomNavigationBar
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val HomeGold = Color(0xFFD9AE50)
private val HomeBackground = Color(0xFF080B14)
private val HomeCard = Color(0xFF111C2C)
private val HoloBlue = Color(0xFF35DFFF)

@Composable
fun HomeScreen(
    selectedPersona: Persona,
    userName: String,
    onNavigate: (AppPage) -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    var currentDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    var selectedCountry by remember {
        mutableStateOf("India")
    }

    var selectedState by remember {
        mutableStateOf("Uttar Pradesh")
    }

    var showCountryDialog by remember {
        mutableStateOf(false)
    }

    var showStateDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentDateTime = LocalDateTime.now()
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

    val greeting = when (currentDateTime.hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    val dateText = currentDateTime.format(
        DateTimeFormatter.ofPattern(
            "EEEE, dd MMM yyyy",
            Locale.getDefault()
        )
    )

    val timeText = currentDateTime.format(
        DateTimeFormatter.ofPattern(
            "hh:mm:ss a",
            Locale.getDefault()
        )
    )

    val breathing = rememberInfiniteTransition(label = "characterBreathing")

    val characterScale by breathing.animateFloat(
        initialValue = 0.985f,
        targetValue = 1.025f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "characterScale"
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
                text = selectedPersona.name,
                color = HomeGold,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            Text(
                text = "☰",
                color = Color.White,
                fontSize = 29.sp,
                modifier = Modifier.align(Alignment.CenterStart)
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

        // HEADER LINE

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .height(1.dp)
                .background(Color(0xFF263448))
        )

        // MAIN CONTENT - NO SCROLL

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            val availableHeight = maxHeight

            val characterSize = (availableHeight * 0.35f)
                .coerceIn(145.dp, 225.dp)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // GREETING

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.075f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "$greeting, ${userName.ifBlank { "Boss" }}!",
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }

                // 3D CHARACTER

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.39f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(characterSize + 16.dp)
                            .scale(characterScale)
                            .shadow(
                                elevation = 22.dp,
                                shape = CircleShape,
                                ambientColor = when (selectedPersona) {
                                    Persona.ULTRON -> Color.Cyan
                                    Persona.LUNA -> Color.Magenta
                                },
                                spotColor = when (selectedPersona) {
                                    Persona.ULTRON -> Color.Cyan
                                    Persona.LUNA -> Color.Magenta
                                }
                            )
                            .border(
                                width = 2.dp,
                                color = when (selectedPersona) {
                                    Persona.ULTRON -> Color(0xFF27DFFF)
                                    Persona.LUNA -> Color(0xFFB44CFF)
                                }.copy(alpha = 0.75f),
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
                            contentDescription = "${selectedPersona.name} animated character",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                }

                // PERSONA NAME AND VOICE STATUS

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f),
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

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Say ${selectedPersona.name} to activate",
                        color = Color(0xFF8B9BB2),
                        fontSize = 11.sp
                    )
                }

                // DATE + TIME CONTAINER

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(HomeCard)
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "DATE",
                            color = HoloBlue,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = dateText,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(38.dp)
                            .background(Color(0xFF293B50))
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "LOCAL TIME",
                            color = HoloBlue,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = timeText,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // TEMPERATURE CONTAINER

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.12f)
                        .clip(RoundedCornerShape(15.dp))
                        .background(HomeCard)
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TEMPERATURE",
                        color = HoloBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "-- °C",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "-- °F",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // WORLD MAP AREA

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.29f)
                        .clip(RoundedCornerShape(17.dp))
                        .background(HomeCard)
                        .padding(9.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GLOBAL MAP",
                            color = HoloBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )

                        Text(
                            text = "◎ Location access needed",
                            color = Color(0xFF8B9BB2),
                            fontSize = 9.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        TextButton(
                            onClick = { showCountryDialog = true }
                        ) {
                            Text(
                                text = "🌐 $selectedCountry",
                                color = Color.White,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }

                        TextButton(
                            onClick = { showStateDialog = true }
                        ) {
                            Text(
                                text = "⌄ $selectedState",
                                color = Color.White,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }
                    }

                    HolographicWorldMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }

        // BOTTOM NAVIGATION

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
            onDismissRequest = {
                showCountryDialog = false
            },
            title = {
                Text("Select Country")
            },
            text = {
                Column {
                    countries.forEach { country ->
                        TextButton(
                            onClick = {
                                selectedCountry = country
                                selectedState = "Select state"
                                showCountryDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = country,
                                color = Color.White
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCountryDialog = false
                    }
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
            onDismissRequest = {
                showStateDialog = false
            },
            title = {
                Text("Select State / Region")
            },
            text = {
                Column {
                    states.forEach { state ->
                        TextButton(
                            onClick = {
                                selectedState = state
                                showStateDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = state,
                                color = Color.White
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showStateDialog = false
                    }
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

@Composable
private fun HolographicWorldMap(
    modifier: Modifier = Modifier
) {
    val mapAnimation = rememberInfiniteTransition(
        label = "mapAnimation"
    )

    val glowAlpha by mapAnimation.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mapGlow"
    )

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0B1727))
            .padding(5.dp)
    ) {
        val w = size.width
        val h = size.height

        // HOLOGRAPHIC GRID

        for (i in 1..5) {
            val y = h * i / 6f

            drawLine(
                color = HoloBlue.copy(alpha = 0.13f),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1f
            )
        }

        for (i in 1..9) {
            val x = w * i / 10f

            drawLine(
                color = HoloBlue.copy(alpha = 0.13f),
                start = Offset(x, 0f),
                end = Offset(x, h),
                strokeWidth = 1f
            )
        }

        // CONTINENT SHAPES

        val continents = listOf(
            listOf(
                0.08f to 0.22f,
                0.16f to 0.12f,
                0.25f to 0.18f,
                0.29f to 0.32f,
                0.25f to 0.48f,
                0.20f to 0.55f,
                0.17f to 0.76f,
                0.12f to 0.63f,
                0.10f to 0.42f
            ),
            listOf(
                0.29f to 0.60f,
                0.34f to 0.66f,
                0.36f to 0.80f,
                0.33f to 0.96f,
                0.29f to 0.82f
            ),
            listOf(
                0.43f to 0.20f,
                0.51f to 0.12f,
                0.60f to 0.18f,
                0.66f to 0.30f,
                0.61f to 0.43f,
                0.55f to 0.39f,
                0.51f to 0.52f,
                0.46f to 0.43f
            ),
            listOf(
                0.52f to 0.48f,
                0.60f to 0.48f,
                0.64f to 0.64f,
                0.60f to 0.88f,
                0.55f to 0.82f,
                0.51f to 0.63f
            ),
            listOf(
                0.65f to 0.18f,
                0.76f to 0.10f,
                0.90f to 0.20f,
                0.94f to 0.36f,
                0.86f to 0.47f,
                0.77f to 0.39f,
                0.70f to 0.32f
            )
        )

        continents.forEach { points ->
            val path = Path().apply {
                points.forEachIndexed { index, point ->
                    val x = point.first * w
                    val y = point.second * h

                    if (index == 0) {
                        moveTo(x, y)
                    } else {
                        lineTo(x, y)
                    }
                }
                close()
            }

            drawPath(
                path = path,
                color = HoloBlue.copy(alpha = 0.85f),
                style = Stroke(width = 1.5f)
            )
        }

        // GLOWING CONNECTION POINTS

        val points = listOf(
            0.20f to 0.30f,
            0.48f to 0.30f,
            0.58f to 0.56f,
            0.77f to 0.27f,
            0.86f to 0.40f
        )

        points.forEach { point ->
            val x = point.first * w
            val y = point.second * h

            drawCircle(
                color = HoloBlue.copy(alpha = 0.20f),
                radius = 9.dp.toPx(),
                center = Offset(x, y)
            )

            drawCircle(
                color = HoloBlue.copy(alpha = glowAlpha),
                radius = 3.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}
