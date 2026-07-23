package com.mydgnbot.data.repository

import android.content.Context
import com.mydgnbot.data.api.ApiPlayer
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.domain.model.Player
import java.io.File

class PlayerEnrichmentRepository(

    private val context: Context,

    private val futGgRepository: FutGgRepository =
        FutGgRepository(),

    private val futGgImageRepository: FutGgImageRepository =
        FutGgImageRepository()

) {


    suspend fun enrich(

        apiPlayer: ApiPlayer

    ): Player {


        var player =

            apiPlayer.toPlayer()



        val futPlayer =

            futGgRepository.getPlayerVersion(

                baseId =
                    player.baseId,

                assetId =
                    player.assetId

            )



        if (futPlayer != null) {


            player =

                player.copy(

                    overall =
                        futPlayer.overall,


                    rarity =
                        futPlayer.rarityGroupName
                            ?: "-",


                    nationId =
                        futPlayer.nationEaId,


                    leagueId =
                        futPlayer.leagueEaId,


                    clubId =
                        futPlayer.clubEaId,


                    skillMoves =
                        futPlayer.skillMoves,


                    weakFoot =
                        futPlayer.weakFoot

                )



            val imagePath =

                futGgImageRepository.getCardImage(

                    cacheDir =
                        context.cacheDir,

                    futGgPlayer =
                        futPlayer

                )



            if (imagePath != null) {


                player =

                    player.copy(

                        imageUrl =
                            imagePath

                    )

            }

        }



        return player

    }

}