package com.mydgnbot.domain.model

data class BotSettings(

    val apiUser: String,

    val secretKey: String,

    val platform: Platform,

    val minimumPrice: Int,

    val maximumPrice: Int,

    val playerMethod: PlayerMethod,

    /**
     * Poll interval in seconds.
     *
     * The UI already warns users not to
     * go below 10 seconds.
     */
    val pollInterval: Int

)
