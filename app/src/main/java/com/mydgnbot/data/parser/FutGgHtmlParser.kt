package com.mydgnbot.data.parser

object FutGgHtmlParser {

    /**
     * Looks through the HTML of a FUT.GG player page
     * and extracts the first official card image URL.
     *
     * Returns null if nothing could be found.
     */
    fun extractCardImageUrl(
        html: String
    ): String? {

        val regex = Regex(
            """https://game-assets\.fut\.gg/2026/player-item-card/[^\s"'<>]+"""
        )

        return regex
            .find(html)
            ?.value
    }
}