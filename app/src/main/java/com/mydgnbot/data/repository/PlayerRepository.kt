package com.mydgnbot.data.repository

import com.mydgnbot.data.api.ApiClient
import com.mydgnbot.data.api.ApiPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepository {


    private val api =
        ApiClient.api


    suspend fun fetchPlayers(

        platform: String,

        playerType: Int,

        minimumPrice: Int,

        maximumPrice: Int

    ): List<ApiPlayer> {


        return withContext(
            Dispatchers.IO
        ) {


            try {


                api.getPlayers(

                    platform = platform,

                    playerType = playerType,

                    minimumPrice = minimumPrice,

                    maximumPrice = maximumPrice

                )


            } catch (
                exception: Exception
            ) {


                emptyList()


            }

        }

    }

}
