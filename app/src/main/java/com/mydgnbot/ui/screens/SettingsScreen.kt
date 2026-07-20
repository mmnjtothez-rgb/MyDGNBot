package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {

    var apiUser by remember { mutableStateOf("") }
    var secretKey by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("Console") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

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

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = "Platform",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

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

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Save")

                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Back")

                }

            }

        }

    }

}
