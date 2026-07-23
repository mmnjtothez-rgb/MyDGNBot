package com.mydgnbot.data.parser

object FutGgHtmlParser {


    fun extractCardImageUrl(

        html: String

    ): String? {


        val patterns = listOf(


            // Full direct URL
            Regex(
                """https://game-assets\.fut\.gg/2026/player-item-card/[^\s"'<>\\]+"""
            ),


            // Escaped JSON URL
            Regex(
                """https:\\/\\/game-assets\.fut\.gg\\/2026\\/player-item-card\\/[^"\\]+"""
            ),


            // Any player-item-card path
            Regex(
                """game-assets\.fut\.gg[^"'<>\\]+player-item-card[^"'<>\\]+"""
            ),


            // Open graph image
            Regex(
                """<meta[^>]+property="og:image"[^>]+content="([^"]+)"""
            ),


            // imageUrl JSON field
            Regex(
                """"imageUrl"\s*:\s*"([^"]+)"""
            )

        )



        for (pattern in patterns) {


            val match =

                pattern.find(html)



            if (match != null) {


                var url =

                    match.groupValues.last()



                url =

                    url
                        .replace("\\/", "/")
                        .replace("\\u0026", "&")



                if (

                    url.startsWith("http")

                ) {

                    return url

                }


                if (

                    url.startsWith("game-assets")

                ) {

                    return "https://$url"

                }

            }

        }



        return null

    }

}