package com.mydgnbot.domain.model

data class Player(

    val playerName: String,

    val rating: Int,

    val platform: Platform,

    val transactionId: String,

    val tradeId: String,

    val assetId: String,

    val resourceId: String,

    val baseId: String,

    val startPrice: Int,

    val buyNowPrice: Int,

    val cardValue: Int,

    val payment: Double,

    val chemistryStyle: String,

    val owners: Int,

    val marketExpiry: Long,

    val status: String

) {

    /**
     * FUT player image.
     *
     * This can be updated later if MyDGN
     * changes their preferred image source.
     */
    val imageUrl: String

        get() =

            "https://game-assets.fut.gg/${resourceId}.png"

}
