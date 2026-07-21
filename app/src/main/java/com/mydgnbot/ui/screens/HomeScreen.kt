package com.mydgnbot.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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


@Composable
fun HomeScreen(

    onSettingsClick: () -> Unit,

    viewModel: HomeViewModel

) {


    val player by viewModel.player.collectAsState()

    val status by viewModel.status.collectAsState()



    Column(

        modifier = Modifier
            .fillMaxSize()
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

                // Bot stop logic will be added next

            },


            onBoughtClick = {

                // Status API update will be added next

            },


            onCancelClick = {

                // Status API update will be added next

            },


            onSettingsClick = onSettingsClick

        )


    }


}
