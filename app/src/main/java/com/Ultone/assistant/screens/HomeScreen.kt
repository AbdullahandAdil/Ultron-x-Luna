
package com.ultone.assistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

private val HomeGold = Color(0xFFD9AE50)
private val HomeBackground = Color(0xFF080B14)

@Composable
fun HomeScreen(
    selectedPersona: Persona,
    userName: String,
    onNavigate: (AppPage) -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current

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

    val greeting = when (java.time.LocalTime.now().hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
    ) {

        // TOP HEADER

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
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

        // MAIN HOME CONTENT

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "$greeting, ${userName.ifBlank { "Boss" }}!",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // ANIMATED CHARACTER CIRCLE

            Box(
                modifier = Modifier
                    .size(260.dp)
                    .offset(y = (-12).dp)
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

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = selectedPersona.name,
                color = HomeGold,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "I'm listening for my name...",
                color = Color.LightGray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Say ${selectedPersona.name} to activate",
                color = Color(0xFF8B9BB2),
                fontSize = 12.sp
            )
        }

        // BOTTOM NAVIGATION

        BottomNavigationBar(
            selectedPage = AppPage.HOME,
            onPageSelected = onNavigate
        )
    }
}
