package com.mydgnbot.data.repository

import com.mydgnbot.data.api.ApiClient
import com.mydgnbot.data.api.ApiPlayer
import com.mydgnbot.data.security.Md5Hasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PlayerRepository {


    private val api =
        ApiClient.api


    suspend fun fetchPlayers(

        user: String,

        secretKey: String,

        platform: String,

        playerType: Int,

        minimumPrice: Int,

        maximumPrice: Int

    ): List<ApiPlayer> {


        return withContext(
            Dispatchers.IO
        ) {


            try {


                val apiPlatform =
                    when (platform.lowercase()) {

                        "console" -> "cons"

                        "pc" -> "pc"

                        else -> platform.lowercase()

                    }


                val timestamp =
                    System.currentTimeMillis() / 1000


                val hashInput =
                    apiPlatform +
                            user +
                            timestamp +
                            secretKey


                val hash =
                    Md5Hasher.generate(
                        hashInput
                    )


                val player =
                    api.getPlayers(

                        user = user,

                        platform = apiPlatform,

                        timestamp = timestamp,

                        hash = hash,

                        maximumBuyOutPrice = maximumPrice,

                        minimumBuyOutPrice = minimumPrice,

                        botApp = "MyDGNBot",

                        playerType = playerType

                    )


                listOf(player)


            } catch (
                exception: Exception
            ) {

                emptyList()

            }

        }

    }

}
