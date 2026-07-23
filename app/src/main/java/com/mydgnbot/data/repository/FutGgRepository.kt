package com.mydgnbot.data.repository

import android.util.Log
import com.mydgnbot.data.api.ApiClient
import com.mydgnbot.data.api.FutGgPlayer
import com.mydgnbot.data.api.FutGgResponse

class FutGgRepository {

    suspend fun testConnection(
        baseId: String
    ): FutGgResponse? {

        return try {

            val response =
                ApiClient.futGgApi.getPlayerVersions(baseId)

            Log.d(
                "FUTGG",
                "Received ${response.data.size} versions"
            )

            response.data.forEach {

                Log.d(
                    "FUTGG",
                    "${it.eaId}  ${it.overall}  ${it.searchableName}"
                )

            }

            response

        } catch (e: Exception) {

            Log.e(
                "FUTGG",
                "Request failed",
                e
            )

            null

        }

    }

    suspend fun getPlayerVersion(

        baseId: String,

        assetId: String

    ): FutGgPlayer? {

        val response =
            testConnection(baseId)
                ?: return null

        return response.data.firstOrNull {

            it.eaId.toString() == assetId

        }

    }

}
