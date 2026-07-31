package com.mydgnbot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Emerald = Color(0xFF42E8B4)
val EmeraldDark = Color(0xFF28C795)
val EmeraldGlow = Color(0xFF0A2417)
val DarkBg = Color(0xFF020503)
val ContainerBgTop = Color(0xFF0B1711)
val ContainerBgBottom = Color(0xFF040A07)
val BorderTop = Color(0xFF1E4230)
val BorderBottom = Color(0xFF0A160F)
val TextMuted = Color(0xFF9CA3AF)

private val DarkColorScheme = darkColorScheme(
    primary = Emerald,
    background = DarkBg,
    surface = ContainerBgTop,
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MyDGNBotTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
