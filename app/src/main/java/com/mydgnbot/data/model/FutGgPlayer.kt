package com.mydgnbot.data.api

data class FutGgPlayer(

    val eaId: Long,

    val basePlayerEaId: Long,

    val overall: Int,

    val rarityGroupName: String?,

    val imageUrl: String?,

    val compactImageUrl: String?,

    val clubEaId: Int?,

    val nationEaId: Int?,

    val leagueEaId: Int?,

    val position: Int?

)
