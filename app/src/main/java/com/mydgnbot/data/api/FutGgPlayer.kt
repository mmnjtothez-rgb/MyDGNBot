package com.mydgnbot.data.api

import com.google.gson.annotations.SerializedName

data class FutGgPlayer(

    @SerializedName("eaId")
    val eaId: Long,

    @SerializedName("basePlayerEaId")
    val basePlayerEaId: Long,

    @SerializedName("overall")
    val overall: Int,

    @SerializedName("rarityGroupName")
    val rarityGroupName: String?,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("compactImageUrl")
    val compactImageUrl: String?,

    @SerializedName("clubEaId")
    val clubEaId: Int?,

    @SerializedName("nationEaId")
    val nationEaId: Int?,

    @SerializedName("leagueEaId")
    val leagueEaId: Int?,

    @SerializedName("position")
    val position: Int?

)
