package com.mydgnbot.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface FutGgApiService {

    @GET("api/fut/players/v2/all-versions/{baseId}/")
    suspend fun getPlayerVersions(

        @Path("baseId")
        baseId: String

    ): FutGgResponse

}
