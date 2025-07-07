package com.example.prayer_time.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.prayer_time.D
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF18181B),  // color Background
    onPrimary = Color(0xFF667085),  // Color secound text in card
    tertiary = Color.White,       // color content
    background = Color(0xFF0C0C0E), // color box
    surface = Color(0xFF18181B), // color of all cards
    onSurface = Color(0xFFE47E5D),   // color first  text in  card
    onBackground = Color.White, // color of icon navBar
    onSecondary = Color(0xFFE47E5D),  //Color Text in Header
    onPrimaryContainer = Color(0xFF000000) // border box and cards
)

@Composable
fun PRAYER_TIMETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = English,
        content = content
    )
}
