
package com.ultone.assistant.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
        DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale.getDefault())
    )

    val timeText = currentDateTime.format(
        DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.getDefault())
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
    ) {

        // TOP HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "☰",
                color = Color.White,
                fontSize = 27.sp
            )

            Text(
                text = selectedPersona.name,
                color = HomeGold,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            IconButton(onClick = onSettingsClick) {
                Text(
                    text = "⚙",
                    color = Color.White,
                    fontSize = 25.sp
                )
            }
        }

        // MAIN CONTENT

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "$greeting, ${userName.ifBlank { "Boss" }}!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ANIMATED CHARACTER

            Box(
                modifier = Modifier
                    .size(235.dp)
                    .offset(y = (-8).dp)
                    .clip(CircleShape)
                    .background(Color(0xFF111C2C)),
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

            Text(
                text = selectedPersona.name,
                color = HomeGold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "I'm listening for my name...",
                color = Color.LightGray,
                fontSize = 12.sp
            )

            Text(
                text = "Say ${selectedPersona.name} to activate",
                color = Color(0xFF8B9BB2),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DATE AND TIME

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InfoCard(
                    title = "DATE",
                    value = dateText,
                    modifier = Modifier.weight(1f)
                )

                InfoCard(
                    title = "LOCAL TIME",
                    value = timeText,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // LOCATION AND TEMPERATURE

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InfoCard(
                    title = "CURRENT LOCATION",
                    value = "Waiting for permission",
                    modifier = Modifier.weight(1f)
                )

                InfoCard(
                    title = "TEMPERATURE",
                    value = "-- °C",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // HOLOGRAPHIC WORLD MAP

            Text(
                text = "GLOBAL CONNECTION",
                color = HoloBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            HolographicWorldMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // BOTTOM NAVIGATION

        BottomNavigationBar(
            selectedPage = AppPage.HOME,
            onPageSelected = onNavigate
        )
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(13.dp))
            .background(Color(0xFF111C2C))
            .padding(horizontal = 11.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = HoloBlue,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Composable
private fun HolographicWorldMap(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0B1727))
            .padding(8.dp)
    ) {
        val w = size.width
        val h = size.height

        // MAP GRID

        for (i in 1..5) {
            val y = h * i / 6f
            drawLine(
                color = HoloBlue.copy(alpha = 0.13f),
                start = androidx.compose.ui.geometry.Offset(0f, y),
                end = androidx.compose.ui.geometry.Offset(w, y),
                strokeWidth = 1f
            )
        }

        for (i in 1..9) {
            val x = w * i / 10f
            drawLine(
                color = HoloBlue.copy(alpha = 0.13f),
                start = androidx.compose.ui.geometry.Offset(x, 0f),
                end = androidx.compose.ui.geometry.Offset(x, h),
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
                style = Stroke(width = 1.6f)
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
                radius = 8.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(x, y)
            )

            drawCircle(
                color = HoloBlue,
                radius = 2.5.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(x, y)
            )
        }
    }
}
