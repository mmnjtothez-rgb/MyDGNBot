package com.mydgnbot.ui.components.fc


object FutAssets {


    fun face(
        assetId: String
    ): String {

        return "https://cdn.futwiz.com/cdn-cgi/image/width=350,quality=100,format=webp/assets/img/fc26/faces/$assetId.png"

    }



    fun cardBackground(
        cardId: Int
    ): String {

        return "https://cdn.futwiz.com/assets/img/fc26/items/$cardId.png"

    }

}
