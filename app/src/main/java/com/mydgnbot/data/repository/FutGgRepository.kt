package com.mydgnbot.data.repository

import com.mydgnbot.data.api.ApiClient
import com.mydgnbot.data.api.FutGgPlayer

class FutGgRepository {

    suspend fun getPlayerVersion(

        baseId: String,

        assetId: String

    ): FutGgPlayer? {

        return try {

            val response =

                ApiClient.futGgApi.getPlayerVersions(baseId)

            response.data.firstOrNull {

                it.eaId.toString() == assetId

            }

        } catch (e: Exception) {

            e.printStackTrace()

            null

        }

    }

}
