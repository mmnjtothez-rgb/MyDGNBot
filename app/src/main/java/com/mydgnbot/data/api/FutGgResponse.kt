package com.mydgnbot.data.api

import com.google.gson.annotations.SerializedName

data class FutGgResponse(

    @SerializedName("data")
    val data: List<FutGgPlayer>

)
