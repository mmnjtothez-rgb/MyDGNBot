package com.mydgnbot.data.repository

import com.mydgnbot.data.api.FutGgApiService

class FutGgRepository(

    private val api: FutGgApiService

) {

    suspend fun getVersion(

        baseId: String,

        assetId: String

    ) =

        api.getPlayerVersions(baseId)

            .firstOrNull {

                it.eaId.toString() == assetId

            }

}
