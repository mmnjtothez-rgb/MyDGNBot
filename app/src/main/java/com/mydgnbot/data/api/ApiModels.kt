package com.mydgnbot.data.api


data class ApiPlayer(

    val code: Int? = null,

    val transactionID: Long? = null,

    val tradeID: String? = null,

    val status: String? = null,

    val platform: String? = null,

    val assetID: Long? = null,

    val resourceID: Long? = null,

    val baseID: Long? = null,

    val playerName: String? = null,

    val search_name: String? = null,

    val position: String? = null,

    val rating: Int? = null,

    val rating_search: Int? = null,

    val startPrice: Int? = null,

    val coinAmount: Int? = null,

    val paymentInUsd: Double? = null,

    val currentRateInUSD: Double? = null,

    val cardValue: Int? = null,

    val cardAndFeeDeducted: Int? = null,

    val lockExpires: Long? = null,

    val ea_expires_at: Long? = null,

    val customerID: Long? = null,

    val ignoreHash: List<String>? = null,

    val isIcon: Boolean? = null,

    val owners: Int? = null,

    val chemistry_style: String? = null,

    val isAtMinPrice: Boolean? = null

)
