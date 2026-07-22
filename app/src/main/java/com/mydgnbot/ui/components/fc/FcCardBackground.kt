package com.mydgnbot.ui.components.fc


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter


@Composable
fun FcCardBackground(

    cardId: Int,

    modifier: Modifier = Modifier

) {


    Image(

        painter =
            rememberAsyncImagePainter(
                FutAssets.cardBackground(cardId)
            ),

        contentDescription =
            "FC Card Background",

        modifier = modifier

    )

}
