package com.mydgnbot.ui.components.fc


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mydgnbot.domain.model.Player


@Composable
fun FcPlayerCard(

    player: Player,

    modifier: Modifier = Modifier

) {


    Box(

        modifier =
            modifier
                .fillMaxWidth()
                .height(300.dp)

    ) {


        FcCardBackground(

            cardId = 161,

            modifier =
                Modifier
                    .matchParentSize()

        )



        AsyncImage(

            model =
                FutAssets.face(
                    player.assetId
                ),

            contentDescription =
                player.playerName,

            modifier =
                Modifier
                    .matchParentSize()

        )

    }

}
