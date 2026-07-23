package com.mydgnbot.data.repository

import android.util.Log
import com.mydgnbot.data.api.ApiClient
import com.mydgnbot.data.api.FutGgPlayer

class FutGgRepository {


    suspend fun getPlayerVersion(

        baseId: String,

        assetId: String

    ): FutGgPlayer? {


        return try {


            val response =

                ApiClient.futGgApi
                    .getPlayerVersions(baseId)



            Log.d(
                "FUTGG",
                "Received ${response.data.size} versions"
            )



            val player =

                response.data.firstOrNull {

                    it.eaId.toString() == assetId

                }



            if (player != null) {

                Log.d(
                    "FUTGG",
                    "Found ${player.searchableName} slug=${player.slug}"
                )

            } else {

                Log.d(
                    "FUTGG",
                    "No matching assetId $assetId"
                )

            }


            player


        } catch (

            e: Exception

        ) {


            Log.e(
                "FUTGG",
                "Request failed",
                e
            )


            null

        }

    }

}