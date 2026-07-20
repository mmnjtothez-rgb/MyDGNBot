package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel
) {

    val settings by viewModel.settings.collectAsState()

    var apiUser by remember {
        mutableStateOf("")
    }

    var secretKey by remember {
        mutableStateOf("")
    }

    var platform by remember {
        mutableStateOf("Console")
    }


    LaunchedEffect(settings) {

        apiUser =
            settings["api_user"] ?: ""

        secretKey =
            settings["secret_key"] ?: ""

        platform =
            settings["platform"] ?: "Console"

    }


    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Settings")
                },

                navigationIcon = {

                    Button(
                        onClick = onBackClick
                    ) {

                        Text("Back")

                    }

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.Top

        ) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text("API Configuration")

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    OutlinedTextField(

                        value = apiUser,

                        onValueChange = {
                            apiUser = it
                        },

                        label = {
                            Text("API Username")
                        },

                        modifier = Modifier.fillMaxWidth()

                    )


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    OutlinedTextField(

                        value = secretKey,

                        onValueChange = {
                            secretKey = it
                        },

                        label = {
                            Text("Secret Key")
                        },

                        modifier = Modifier.fillMaxWidth()

                    )

                }

            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text("Bot Configuration")

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    Text("Platform")


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(

                            selected = platform == "Console",

                            onClick = {
                                platform = "Console"
                            }

                        )

                        Text("Console")

                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(

                            selected = platform == "PC",

                            onClick = {
                                platform = "PC"
                            }

                        )

                        Text("PC")

                    }

                }

            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            Button(

                onClick = {

                    viewModel.saveSettings(
                        apiUser = apiUser,
                        secretKey = secretKey,
                        platform = platform
                    )

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Save")

            }

        }

    }
}
