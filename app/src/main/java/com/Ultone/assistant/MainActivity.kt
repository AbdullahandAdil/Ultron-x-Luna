
package com.ultone.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.ultone.assistant.components.BottomNavigationBar
import com.ultone.assistant.data.AppPreferences
import com.ultone.assistant.data.Persona
import com.ultone.assistant.screens.*
import com.ultone.assistant.theme.UltronLunaTheme
import kotlinx.coroutines.delay

enum class AppPage {
    SPLASH, CUSTOMIZE, HOME, CHAT, AGENTS, HISTORY, ABOUT, SETTINGS
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UltronLunaTheme {
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

@Composable
fun UltronLunaApp() {

    val context = LocalContext.current

    var currentPage by remember {
        mutableStateOf(AppPage.SPLASH)
    }

    var previousPage by remember {
        mutableStateOf(AppPage.HOME)
    }

    var selectedPersona by remember {
        mutableStateOf(AppPreferences.getPersona(context))
    }

    var userName by remember {
        mutableStateOf(AppPreferences.getUserName(context))
    }

    // Splash Screen Transition
    LaunchedEffect(currentPage) {
        if (currentPage == AppPage.SPLASH) {
            delay(1800)

            currentPage = if (
                AppPreferences.isSetupComplete(context)
            ) {
                AppPage.HOME
            } else {
                AppPage.CUSTOMIZE
            }
        }
    }

    // Save Persona
    fun updatePersona(persona: Persona) {
        selectedPersona = persona
        AppPreferences.savePersona(context, persona)
    }

    // Navigation
    fun navigate(page: AppPage) {
        currentPage = page
    }

    // Open Settings
    fun openSettings() {
        previousPage = currentPage
        currentPage = AppPage.SETTINGS
    }

    // Settings Back
    BackHandler(
        enabled = currentPage == AppPage.SETTINGS
    ) {
        currentPage = previousPage
    }

    when (currentPage) {

        AppPage.SPLASH -> {
            SplashScreen()
        }

        AppPage.CUSTOMIZE -> {
            CustomizeScreen(
                selectedPersona = selectedPersona,
                userName = userName,
                onPersonaSelected = {
                    updatePersona(it)
                },
                onNameChanged = {
                    userName = it
                },
                onSave = {
                    AppPreferences.saveUserName(context, userName)
                    AppPreferences.savePersona(context, selectedPersona)
                    AppPreferences.setSetupComplete(context, true)
                    currentPage = AppPage.HOME
                },
                onSettingsClick = {
                    openSettings()
                }
            )
        }

        AppPage.HOME -> {
            // HomeScreen already contains BottomNavigationBar.
            // Do not wrap it in PageWithNavigation.
            HomeScreen(
                selectedPersona = selectedPersona,
                userName = userName,
                onNavigate = {
                    navigate(it)
                },
                onSettingsClick = {
                    openSettings()
                }
            )
        }

        AppPage.CHAT -> {
            PageWithNavigation(
                selectedPage = AppPage.CHAT,
                onNavigate = { navigate(it) }
            ) {
                ChatScreen(
                    selectedPersona = selectedPersona,
                    userName = userName
                )
            }
        }

        AppPage.AGENTS -> {
            PageWithNavigation(
                selectedPage = AppPage.AGENTS,
                onNavigate = { navigate(it) }
            ) {
                AgentsScreen()
            }
        }

        AppPage.HISTORY -> {
            PageWithNavigation(
                selectedPage = AppPage.HISTORY,
                onNavigate = { navigate(it) }
            ) {
                HistoryScreen()
            }
        }

        AppPage.ABOUT -> {
            PageWithNavigation(
                selectedPage = AppPage.ABOUT,
                onNavigate = { navigate(it) }
            ) {
                AboutScreen()
            }
        }

        AppPage.SETTINGS -> {
            SettingsScreen(
                selectedPersona = selectedPersona,
                onPersonaChanged = {
                    updatePersona(it)
                }
            )
        }
    }
}

@Composable
fun PageWithNavigation(
    selectedPage: AppPage,
    onNavigate: (AppPage) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            content()
        }

        BottomNavigationBar(
            selectedPage = selectedPage,
            onPageSelected = onNavigate
        )
    }
}
