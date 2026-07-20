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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel
) {


    val status by viewModel.status.collectAsState()

    val player by viewModel.player.collectAsState()



    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("MyDGNBot")

                }

            )

        }

    ) { padding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.Center

        ) {


            Card(

                modifier = Modifier
                    .fillMaxWidth()

            ) {


                Column(

                    modifier = Modifier.padding(20.dp)

                ) {


                    Text(
                        text = "Status: $status"
                    )


                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )


                    Text(

                        text =
                            player?.playerName
                                ?: "Waiting for player..."

                    )


                    if (player != null) {


                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )


                        Text(
                            text =
                                "Rating: ${player?.rating}"
                        )


                        Text(
                            text =
                                "Buy Now: ${player?.buyNowPrice}"
                        )

                    }

                }

            }



            Spacer(
                modifier = Modifier.height(24.dp)
            )



            Button(

                onClick = {

                    viewModel.fetchPlayer()

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Find Player")

            }



            Spacer(
                modifier = Modifier.height(12.dp)
            )



            Button(

                onClick = onSettingsClick,

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Settings")

            }

        }

    }

}
