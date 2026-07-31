package com.mydgnbot.domain.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("playerName")
    val playerName: String,

    @SerializedName("rating")
    val rating: Int,

    @SerializedName("platform")
    val platform: Platform,

    @SerializedName("transactionID")
    val transactionId: String = "",

    @SerializedName("tradeID")
    val tradeId: String = "",

    @SerializedName("assetID")
    val assetId: String = "",

    @SerializedName("resourceID")
    val resourceId: String = "",

    @SerializedName("baseID")
    val baseId: String = "",

    @SerializedName("startPrice")
    val startPrice: Int,

    // Maps MyDGN "coinAmount" field -> buyNowPrice
    @SerializedName("coinAmount")
    val buyNowPrice: Int,

    @SerializedName("cardValue")
    val cardValue: Int,

    // Maps MyDGN "paymentInUsd" field -> payment
    @SerializedName("paymentInUsd")
    val payment: Double,

    // Maps MyDGN "chemistry_style" field -> chemistryStyle
    @SerializedName("chemistry_style")
    val chemistryStyle: String = "",

    @SerializedName("owners")
    val owners: Int = 0,

    // Maps MyDGN "ea_expires_at" field -> marketExpiry
    @SerializedName("ea_expires_at")
    val marketExpiry: Long,

    @SerializedName("lockExpires")
    val lockExpires: Long,

    @SerializedName("status")
    val status: String = "",

    // Optional metadata fields
    val overall: Int? = null,
    val rarity: String? = null,
    val imageUrl: String? = null,
    val compactImageUrl: String? = null,
    val nationId: Int? = null,
    val leagueId: Int? = null,
    val clubId: Int? = null,
    val skillMoves: Int? = null,
    val weakFoot: Int? = null,
    val preferredFoot: Int? = null,
    val position: Int? = null
)
