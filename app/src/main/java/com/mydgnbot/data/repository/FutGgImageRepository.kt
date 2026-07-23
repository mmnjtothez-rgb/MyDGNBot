package com.mydgnbot.data.repository

import com.mydgnbot.data.parser.FutGgHtmlParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class FutGgImageRepository {

    private val client = OkHttpClient()

    /**
     * Downloads the official FUT.GG card image.
     *
     * Returns:
     * - Cached image path if already downloaded
     * - Newly downloaded image path
     * - null if anything fails
     */
    suspend fun getCardImage(

        cacheDir: File,

        baseId: String,

        assetId: String,

        playerName: String

    ): String? {

        try {

            val slug =
                playerName
                    .lowercase()
                    .replace(" ", "-")
                    .replace(".", "")
                    .replace("'", "")

            val pageUrl =
                "https://www.fut.gg/players/$baseId-$slug/26-$assetId/"

            val cacheFolder =
                File(cacheDir, "futgg")

            if (!cacheFolder.exists()) {

                cacheFolder.mkdirs()

            }

            val cachedImage =

                File(
                    cacheFolder,
                    "$assetId.webp"
                )

            if (cachedImage.exists()) {

                return cachedImage.absolutePath

            }

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
                ) ?: return null

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

            val bytes =

                response.body

                    ?.bytes()

                    ?: return null

            cachedImage.writeBytes(bytes)

            return cachedImage.absolutePath

        } catch (

            e: Exception

        ) {

            e.printStackTrace()

            return null

        }

    }

}