package com.mydgnbot.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MyDgnApiService {

    @GET("api/doc")
    suspend fun getPlayers(
        @Query("platform")
        platform: String,

        @Query("player_type")
        playerType: Int,

        @Query("min_buy_price")
        minimumPrice: Int,

        @Query("max_buy_price")
        maximumPrice: Int
    ): List<ApiPlayer>

}
