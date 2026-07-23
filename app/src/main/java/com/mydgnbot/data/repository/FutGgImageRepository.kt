package com.mydgnbot.data.repository

import com.mydgnbot.data.api.FutGgPlayer
import com.mydgnbot.data.parser.FutGgHtmlParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class FutGgImageRepository {


    private val client =
        OkHttpClient()



    suspend fun getCardImage(

        cacheDir: File,

        futGgPlayer: FutGgPlayer

    ): String? {


        return try {


            val assetId =
                futGgPlayer.eaId.toString()



            val cacheFolder =
                File(
                    cacheDir,
                    "futgg"
                )


            if (!cacheFolder.exists()) {

                cacheFolder.mkdirs()

            }



            val cachedFile =
                File(
                    cacheFolder,
                    "$assetId.webp"
                )



            if (cachedFile.exists()) {

                return cachedFile.absolutePath

            }



            val pageUrl =

                "https://www.fut.gg/players/${futGgPlayer.slug}/26-$assetId/"



            val pageRequest =

                Request.Builder()

                    .url(pageUrl)

                    .header(
                        "User-Agent",
                        "Mozilla/5.0"
                    )

                    .build()



            val html =

                client.newCall(pageRequest)

                    .execute()

                    .body

                    ?.string()

                    ?: return null



            val imageUrl =

                FutGgHtmlParser.extractCardImageUrl(
                    html
                )
                    ?: return null



            val imageRequest =

                Request.Builder()

                    .url(imageUrl)

                    .header(
                        "User-Agent",
                        "Mozilla/5.0"
                    )

                    .build()



            val response =

                client.newCall(imageRequest)

                    .execute()



            if (!response.isSuccessful) {

                return null

            }



            val imageBytes =

                response.body

                    ?.bytes()

                    ?: return null



            cachedFile.writeBytes(
                imageBytes
            )



            cachedFile.absolutePath



        } catch (

            e: Exception

        ) {


            e.printStackTrace()

            null

        }

    }

}