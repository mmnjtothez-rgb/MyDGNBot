package com.mydgnbot.ui

import androidx.compose.runtime.Composable
import com.mydgnbot.ui.navigation.AppNavigation
import com.mydgnbot.ui.theme.MyDGNBotTheme

@Composable
fun MyDGNApp() {
    MyDGNBotTheme {
        AppNavigation()
    }
}