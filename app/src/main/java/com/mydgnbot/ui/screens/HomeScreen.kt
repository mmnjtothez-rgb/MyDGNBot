package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mydgnbot.ui.components.ActionButtons
import com.mydgnbot.ui.components.BotActionState
import com.mydgnbot.ui.components.HomeHeader
import com.mydgnbot.ui.components.PlayerCard
import com.mydgnbot.ui.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

    onSettingsClick: () -> Unit,

    viewModel: HomeViewModel

) {


    val player by viewModel.player.collectAsState()

    val status by viewModel.status.collectAsState()



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

                .padding(16.dp)

                .verticalScroll(
                    rememberScrollState()
                ),

            verticalArrangement = Arrangement.Top

        ) {


            HomeHeader(

                platform = "PC",

                method = "Quick Sell",

                interval = "10",

                connected = true

            )



            Spacer(

                modifier = Modifier.height(16.dp)

            )



            PlayerCard(

                player = player

            )



            Spacer(

                modifier = Modifier.height(16.dp)

            )



            ActionButtons(

                state =

                    if (player != null)

                        BotActionState.PLAYER_FOUND

                    else if (status == "Searching...")

                        BotActionState.SEARCHING

                    else

                        BotActionState.IDLE,


                onStartClick = {

                    viewModel.fetchPlayer()

                },


                onStopClick = {

                },


                onBoughtClick = {

                },


                onCancelClick = {

                },


                onSettingsClick = onSettingsClick

            )

        }

    }

}
