package com.mydgnbot.ui

import androidx.compose.runtime.Composable
import com.mydgnbot.ui.navigation.AppNavigation
import com.mydgnbot.ui.theme.MyDGNTheme

@Composable
fun MyDGNApp() {

    MyDGNTheme {

        AppNavigation()

    }
}
