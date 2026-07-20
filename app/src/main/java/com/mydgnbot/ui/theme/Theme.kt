package com.mydgnbot.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MyDGNColorScheme = darkColorScheme()

@Composable
fun MyDGNTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = MyDGNColorScheme,
        content = content
    )
}
