package com.mydgnbot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MyDGNDarkColors = darkColorScheme(

    primary = Emerald,
    onPrimary = TextPrimary,

    secondary = EmeraldLight,
    onSecondary = TextPrimary,

    tertiary = Gold,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    outline = DividerColor,

    error = Danger,
    onError = TextPrimary

)

@Composable
fun MyDGNBotTheme(

    content: @Composable () -> Unit

) {

    MaterialTheme(

        colorScheme = MyDGNDarkColors,

        typography = Typography,

        shapes = Shapes,

        content = content

    )

}