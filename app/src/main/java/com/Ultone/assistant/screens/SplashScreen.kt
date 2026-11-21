
package com.ultone.assistant.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ultone.assistant.R

@Composable
fun SplashScreen() {

    val scale = remember {
        Animatable(0.5f)
    }

    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000)
        )

        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(800)
        )
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .graphicsLayer {
                this.alpha = alpha.value
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.app_logo
                ),
                contentDescription = "Ultron x Luna Logo",
                modifier = Modifier
                    .size(180.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
            )

            Text(
                text = "ULTRON X LUNA",
                color = Color(0xFFD9AE50),
                fontSize = 25.sp,
                letterSpacing = 3.sp,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "YOUR PERSONAL AI ASSISTANT",
                color = Color.LightGray,
                fontSize = 11.sp,
                letterSpacing = 2.sp
            )
        }
    }
}
