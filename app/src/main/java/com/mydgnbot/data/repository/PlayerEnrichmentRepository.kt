package com.mydgnbot.data.repository

import com.mydgnbot.data.api.ApiPlayer
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.domain.model.Player

class PlayerEnrichmentRepository(

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


            /*
             We will connect the image downloader
             after we add a safe cacheDir provider.

             For now, keep metadata working.
            */


        }



        return player

    }

}