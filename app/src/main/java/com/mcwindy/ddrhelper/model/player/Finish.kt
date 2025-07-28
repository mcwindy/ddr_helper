package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Finish(
    val points: Int,
    @param:Json(name = "total_finishes") val totalFinishes: Int,
    val finishes: Int,
    val rank: Int?, // default if Unfinished
    val time: Double?, // default if Unfinished
    @param:Json(name = "first_finish") val firstFinish: Double? // default if Unfinished

)
//{"timestamp": 1587137164.0, "map": "StepByStep", "time": 414.24}
