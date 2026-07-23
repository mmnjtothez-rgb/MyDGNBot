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

        val player =

            apiPlayer.toPlayer()

        // Temporary test.
        // If we can display this in the UI,
        // we know the enrichment pipeline works.

        return player.copy(

            rarity = "TEST OK"

        )

    }

}
