package com.mydgnbot.data.mapper


import com.mydgnbot.data.api.ApiPlayer
import com.mydgnbot.domain.model.Platform
import com.mydgnbot.domain.model.Player


object ApiPlayerMapper {


    fun ApiPlayer.toPlayer(): Player {


        return Player(

            playerName =
                playerName ?: "Unknown Player",


            rating =
                rating ?: 0,


            platform =
                when (platform?.lowercase()) {

                    "pc" ->
                        Platform.PC

                    else ->
                        Platform.CONSOLE

                },


            transactionId =
                transactionID?.toString()
                    ?: "",


            tradeId =
                tradeID ?: "",


            assetId =
                assetID?.toString()
                    ?: "",


            resourceId =
                resourceID?.toString()
                    ?: "",


            baseId =
                baseID?.toString()
                    ?: "",


            startPrice =
                startPrice ?: 0,


            buyNowPrice =
                coinAmount ?: 0,


            cardValue =
                cardValue ?: 0,


            payment =
                paymentInUsd ?: 0.0,


            chemistryStyle =
                chemistry_style ?: "None",


            owners =
                owners ?: 0,


            marketExpiry =
                ea_expires_at ?: 0L,


            status =
                status ?: ""

        )

    }

}
