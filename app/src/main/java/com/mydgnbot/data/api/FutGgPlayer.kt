package com.mydgnbot.data.api

import com.google.gson.annotations.SerializedName

data class FutGgPlayer(

    @SerializedName("eaId")
    val eaId: Long,

    @SerializedName("basePlayerEaId")
    val basePlayerEaId: Long,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("searchableName")
    val searchableName: String,

    @SerializedName("overall")
    val overall: Int,

    @SerializedName("nationEaId")
    val nationEaId: Int,

    @SerializedName("leagueEaId")
    val leagueEaId: Int,

    @SerializedName("clubEaId")
    val clubEaId: Int,

    @SerializedName("rarityEaId")
    val rarityEaId: Int,

    @SerializedName("position")
    val position: Int,

    @SerializedName("skillMoves")
    val skillMoves: Int,

    @SerializedName("weakFoot")
    val weakFoot: Int,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("compactImageUrl")
    val compactImageUrl: String?,

    @SerializedName("rarityGroupName")
    val rarityGroupName: String?

)
