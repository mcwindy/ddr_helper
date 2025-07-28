package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Types(
    val points: Points,
    @param:Json(name = "team_rank") val teamRank: Ranks,
    val rank: Ranks,
    val maps: Map<String, Finish>
)
