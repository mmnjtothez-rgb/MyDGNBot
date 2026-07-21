package com.mydgnbot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


enum class BotActionState {

    IDLE,

    SEARCHING,

    PLAYER_FOUND

}


@Composable
fun ActionButtons(

    state: BotActionState,

    onStartClick: () -> Unit,

    onStopClick: () -> Unit,

    onBoughtClick: () -> Unit,

    onCancelClick: () -> Unit,

    onSettingsClick: () -> Unit

) {


    when (state) {


        BotActionState.IDLE -> {


            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {


                Button(

                    onClick = onStartClick,

                    modifier = Modifier.weight(1f)

                ) {

                    Text("Start Bot")

                }



                OutlinedButton(

                    onClick = onSettingsClick,

                    modifier = Modifier.weight(1f)

                ) {

                    Text("Settings")

                }


            }


        }



        BotActionState.SEARCHING -> {


            Button(

                onClick = onStopClick,

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Stop Bot")

            }


        }



        BotActionState.PLAYER_FOUND -> {


            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {


                Button(

                    onClick = onBoughtClick,

                    modifier = Modifier.weight(1f)

                ) {

                    Text("Bought")

                }



                OutlinedButton(

                    onClick = onCancelClick,

                    modifier = Modifier.weight(1f)

                ) {

                    Text("Cancel")

                }


            }


        }


    }


}
