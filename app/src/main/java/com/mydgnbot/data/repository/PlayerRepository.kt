package com.mydgnbot.data.repository

import com.mydgnbot.data.api.ApiClient
import com.mydgnbot.data.api.ApiPlayer
import com.mydgnbot.data.api.StatusRequest
import com.mydgnbot.data.api.StatusResponse
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
                    normalizePlatform(platform)


                val timestamp =
                    System.currentTimeMillis() / 1000


                val hash =
                    Md5Hasher.generate(
                        apiPlatform +
                                user +
                                timestamp +
                                secretKey
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



    suspend fun updateOrderStatus(

        user: String,

        secretKey: String,

        platform: String,

        transactionId: Int,

        status: String,

        eaEmail: String = "",

        code: Int? = null

    ): StatusResponse? {


        return withContext(
            Dispatchers.IO
        ) {


            try {


                val apiPlatform =
                    normalizePlatform(platform)


                val timestamp =
                    System.currentTimeMillis() / 1000


                val hash =
                    Md5Hasher.generate(

                        apiPlatform +
                                user +
                                timestamp +
                                secretKey

                    )


                val emailHash =

                    if (
                        status == "bought" &&
                        eaEmail.isNotBlank()
                    ) {

                        Md5Hasher.generate(
                            eaEmail
                        )

                    } else {

                        ""

                    }



                val request =

                    StatusRequest(

                        user = user,

                        platform = apiPlatform,

                        timestamp = timestamp,

                        hash = hash,

                        transactionID = transactionId,

                        status = status,

                        emailHash = emailHash,

                        code = code

                    )



                api.updateStatus(
                    request
                )


            } catch (
                exception: Exception
            ) {

                null

            }


        }

    }



    private fun normalizePlatform(
        platform: String
    ): String {


        return when (
            platform.lowercase()
        ) {

            "console" ->
                "cons"

            "pc" ->
                "pc"

            else ->
                platform.lowercase()

        }

    }

}
