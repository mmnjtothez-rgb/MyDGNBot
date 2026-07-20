package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel
) {

    val settings by viewModel.settings.collectAsState()

    var apiUser by remember { mutableStateOf("") }

    var secretKey by remember { mutableStateOf("") }

    var platform by remember { mutableStateOf("Console") }

    var minimumPrice by remember { mutableStateOf("4000") }

    var maximumPrice by remember { mutableStateOf("300000") }

    var playerType by remember { mutableStateOf("2") }

    var pollInterval by remember { mutableStateOf("10") }


    LaunchedEffect(settings) {

        apiUser = settings["api_user"] ?: ""

        secretKey = settings["secret_key"] ?: ""

        platform = settings["platform"] ?: "Console"

        minimumPrice = settings["minimum_price"] ?: "4000"

        maximumPrice = settings["maximum_price"] ?: "300000"

        playerType = settings["player_type"] ?: "2"

        pollInterval = settings["poll_interval"] ?: "10"

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
                .padding(horizontal = 12.dp),

            verticalArrangement = Arrangement.Top

        ) {


            Text("API Configuration")


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            OutlinedTextField(

                value = apiUser,

                onValueChange = {
                    apiUser = it
                },

                label = {
                    Text("API Username")
                },

                singleLine = true,

                modifier = Modifier.fillMaxWidth()

            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            OutlinedTextField(

                value = secretKey,

                onValueChange = {
                    secretKey = it
                },

                label = {
                    Text("Secret Key")
                },

                singleLine = true,

                modifier = Modifier.fillMaxWidth()

            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Text("Bot Configuration")


            Spacer(
                modifier = Modifier.height(4.dp)
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


                RadioButton(

                    selected = platform == "PC",

                    onClick = {
                        platform = "PC"
                    }

                )

                Text("PC")

            }


            Text("Player Method")


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(

                    selected = playerType == "1",

                    onClick = {
                        playerType = "1"
                    }

                )

                Text("Safe")


                RadioButton(

                    selected = playerType == "2",

                    onClick = {
                        playerType = "2"
                    }

                )

                Text("Quicksell")

            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            OutlinedTextField(

                value = minimumPrice,

                onValueChange = {
                    minimumPrice = it
                },

                label = {
                    Text("Minimum Buy Price")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

                singleLine = true,

                modifier = Modifier.fillMaxWidth()

            )


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            OutlinedTextField(

                value = maximumPrice,

                onValueChange = {
                    maximumPrice = it
                },

                label = {
                    Text("Maximum Buy Price")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

                singleLine = true,

                modifier = Modifier.fillMaxWidth()

            )


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            OutlinedTextField(

                value = pollInterval,

                onValueChange = {
                    pollInterval = it
                },

                label = {
                    Text("Search Interval")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

                singleLine = true,

                modifier = Modifier.fillMaxWidth()

            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            Button(

                onClick = {

                    viewModel.saveSettings(

                        apiUser = apiUser,

                        secretKey = secretKey,

                        platform = platform,

                        minimumPrice = minimumPrice,

                        maximumPrice = maximumPrice,

                        playerType = playerType,

                        pollInterval = pollInterval

                    )

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Save")

            }

        }

    }
}
