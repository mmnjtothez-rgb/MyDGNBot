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

    val status: String,

    // ---------- FUT.GG ----------

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
