package com.mydgnbot.domain.model

sealed interface PlayerResult {

    data class Success(
        val players: List<Player>
    ) : PlayerResult

    data class Error(
        val message: String
    ) : PlayerResult

}
