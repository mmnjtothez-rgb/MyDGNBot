package com.mydgnbot.data.repository

import com.mydgnbot.data.api.ApiPlayer
import com.mydgnbot.data.mapper.ApiPlayerMapper.toPlayer
import com.mydgnbot.domain.model.Player

class PlayerEnrichmentRepository(

    private val futGgRepository: FutGgRepository = FutGgRepository()

) {

    suspend fun enrich(

        apiPlayer: ApiPlayer

    ): Player {

        val player = apiPlayer.toPlayer()

        val futPlayer =

            futGgRepository.getPlayerVersion(

                baseId = player.baseId,

                assetId = player.assetId

            )

        if (futPlayer == null) {

            return player.copy(

                rarity = "NOT FOUND"

            )

        }

        return player.copy(

            rating =
                futPlayer.overall,

            rarity =
                futPlayer.rarityGroupName,

            imageUrl =
                futPlayer.imageUrl,

            compactImageUrl =
                futPlayer.compactImageUrl,

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

    }

}