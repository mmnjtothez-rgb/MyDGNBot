package com.mydgnbot.ui.components.fc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage


@Composable
fun FcCardBackground(

    cardId: Int,

    modifier: Modifier = Modifier

) {

    AsyncImage(

        model =
            "https://cdn.futwiz.com/assets/img/fc26/items/$cardId.png",

        contentDescription =
            "Card Background",

        modifier = modifier

    )

}
