
package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF080B14)
                ) {
                    UltronLunaApp()
                }
            }
        }
    }
}

enum class AppPage {
    SPLASH,
    CUSTOMIZE,
    HOME,
    CHAT,
    AGENTS,
    HISTORY,
    ABOUT,
    SETTINGS
}

enum class Persona {
    ULTRON,
    LUNA
}

@Composable
fun UltronLunaApp() {

    var currentPage by remember {
        mutableStateOf(AppPage.SPLASH)
    }

    var selectedPersona by remember {
        mutableStateOf(Persona.ULTRON)
    }

    var userName by remember {
        mutableStateOf("Boss")
    }

    when (currentPage) {

        AppPage.SPLASH -> {
            PlaceholderScreen("ULTRON X LUNA")
        }

        AppPage.CUSTOMIZE -> {
            PlaceholderScreen("CUSTOMIZE")
        }

        AppPage.HOME -> {
            PlaceholderScreen(selectedPersona.name)
        }

        AppPage.CHAT -> {
            PlaceholderScreen("CHAT")
        }

        AppPage.AGENTS -> {
            PlaceholderScreen("AGENTS")
        }

        AppPage.HISTORY -> {
            PlaceholderScreen("HISTORY")
        }

        AppPage.ABOUT -> {
            PlaceholderScreen("ABOUT")
        }

        AppPage.SETTINGS -> {
            PlaceholderScreen("SETTINGS")
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}