package com.mydgnbot.ui.components.fc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun FcCardBackground(

    cardId: Int,

    modifier: Modifier = Modifier

) {


    AsyncImage(

        model = ImageRequest.Builder(
            LocalContext.current
        )
            .data(
                FutAssets.cardBackground(cardId)
            )
            .crossfade(true)
            .build(),


        contentDescription = "FC Card Background",


        modifier = modifier

    )

}
