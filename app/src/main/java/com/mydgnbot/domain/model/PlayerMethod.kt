package com.mydgnbot.domain.model

enum class PlayerMethod(
    val apiValue: Int,
    val displayName: String
) {

    SAFE(
        apiValue = 1,
        displayName = "Safe Method"
    ),

    QUICKSELL(
        apiValue = 2,
        displayName = "Quicksell Method"
    );

    companion object {

        fun fromValue(
            value: Int
        ): PlayerMethod {

            return entries.firstOrNull {

                it.apiValue == value

            } ?: QUICKSELL

        }

    }

}
