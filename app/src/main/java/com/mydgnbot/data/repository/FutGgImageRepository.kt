package com.mydgnbot.data.repository

import com.mydgnbot.data.api.FutGgPlayer
import com.mydgnbot.data.parser.FutGgHtmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File


class FutGgImageRepository {


    companion object {

        var lastStatus =
            "Idle"

    }



    private val client =
        OkHttpClient()



    suspend fun getCardImage(

        cacheFolder: File,

        futGgPlayer: FutGgPlayer

    ): String? = withContext(Dispatchers.IO) {


        try {


            lastStatus =
                "Opening FUT.GG page"



            if (!cacheFolder.exists()) {

                cacheFolder.mkdirs()

            }



            val assetId =

                futGgPlayer.eaId.toString()



            val cachedFile =

                File(

                    cacheFolder,

                    "$assetId.webp"

                )



            if (cachedFile.exists()) {


                lastStatus =
                    "Image loaded from cache"


                return@withContext cachedFile.absolutePath

            }



            val pageUrl =

                "https://www.fut.gg/players/${futGgPlayer.slug}/26-$assetId/"



            val pageRequest =

                Request.Builder()

                    .url(pageUrl)

                    .header(

                        "User-Agent",

                        "Mozilla/5.0 (Android)"

                    )

                    .header(

                        "Accept",

                        "text/html"

                    )

                    .build()



            val html =

                client.newCall(pageRequest)

                    .execute()

                    .body

                    ?.string()



                    ?: run {

                        lastStatus =
                            "Empty page"

                        return@withContext null

                    }



            lastStatus =

                "FUT.GG page loaded"



            val imageUrl =

                FutGgHtmlParser.extractCardImageUrl(

                    html

                )



            if (imageUrl == null) {


                lastStatus =

                    "No image URL found"


                return@withContext null

            }



            lastStatus =

                "Image URL found"



            val imageRequest =

                Request.Builder()

                    .url(imageUrl)

                    .header(

                        "User-Agent",

                        "Mozilla/5.0 (Android)"

                    )

                    .header(

                        "Accept",

                        "image/webp,image/*"

                    )

                    .build()



            val response =

                client.newCall(imageRequest)

                    .execute()



            if (!response.isSuccessful) {


                lastStatus =

                    "Image failed ${response.code}"


                return@withContext null

            }



            val bytes =

                response.body

                    ?.bytes()



                    ?: run {

                        lastStatus =
                            "Empty image bytes"

                        return@withContext null

                    }



            cachedFile.writeBytes(

                bytes

            )



            lastStatus =

                "Image cached"



            cachedFile.absolutePath



        } catch (

            e: Exception

        ) {


            lastStatus =

                "Error: ${e.javaClass.simpleName}"


            null

        }

    }

}