package com.mydgnbot.ui.components.fc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext


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
                "https://cdn.futwiz.com/assets/img/fc26/items/$cardId.png"
            )
            .crossfade(true)
            .build(),

        contentDescription = "FC Card",

        modifier = modifier

    )

}
