package com.mydgnbot.domain.model

enum class Platform(
    val apiValue: String,
    val displayName: String
) {

    CONSOLE(
        apiValue = "Console",
        displayName = "Console"
    ),

    PC(
        apiValue = "PC",
        displayName = "PC"
    );

    companion object {

        fun fromValue(
            value: String
        ): Platform {

            return entries.firstOrNull {

                it.apiValue.equals(
                    value,
                    ignoreCase = true
                )

            } ?: CONSOLE

        }

    }

}
