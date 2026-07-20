package com.mydgnbot.data.api

data class ApiPlayer(

    val transactionID: String? = null,

    val tradeID: String? = null,

    val status: String? = null,

    val platform: String? = null,

    val assetID: String? = null,

    val resourceID: String? = null,

    val baseID: String? = null,

    val playerName: String? = null,

    val rating: Int? = null,

    val startPrice: Int? = null,

    val coinAmount: Int? = null,

    val paymentInUsd: Double? = null,

    val cardValue: Int? = null,

    val ea_expires_at: Long? = null,

    val owners: Int? = null,

    val chemistry_style: String? = null
)
