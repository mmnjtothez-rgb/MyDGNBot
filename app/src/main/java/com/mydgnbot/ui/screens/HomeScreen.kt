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
import androidx.compose.material3.MaterialTheme
import com.mydgnbot.ui.components.ActionButtons
import com.mydgnbot.ui.components.ActivityLogCard
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

    val settings by viewModel.settings.collectAsState()
    val status by viewModel.status.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()

    val isRunning by viewModel.isRunning.collectAsState()

    val logs by viewModel.logs.collectAsState()



    val platform =
        settings["platform"]
            ?: "Console"



    val method =
        if (settings["player_type"] == "1") {

            "Safe"

        } else {

            "Quick Sell"

        }



    val interval =
        settings["poll_interval"]
            ?: "10"



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


            verticalArrangement =
                Arrangement.Top

        ) {


            HomeHeader(

                platform = platform,

                method = method,

                interval = interval,

                connected = isOnline

            )

Spacer(
    modifier = Modifier.height(12.dp)
)


Text(

    text = "Status: $status",

    style = MaterialTheme.typography.bodyMedium

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

                state = when {


                    player != null ->

                        BotActionState.PLAYER_FOUND



                    isRunning ->

                        BotActionState.SEARCHING



                    else ->

                        BotActionState.IDLE

                },


                onStartClick = {

                    viewModel.startBot()

                },


                onStopClick = {

                    viewModel.stopBot()

                },


                onBoughtClick = {

                    viewModel.markBought()

                },


                onCancelClick = {

                    viewModel.cancelPlayer()

                },


                onSettingsClick = onSettingsClick

            )



            Spacer(

                modifier = Modifier.height(16.dp)

            )



            ActivityLogCard(

                logs = logs

            )

        }

    }

}
