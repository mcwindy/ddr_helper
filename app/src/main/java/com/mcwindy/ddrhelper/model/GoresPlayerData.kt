package com.mcwindy.ddrhelper.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class GoresPoints(
    @Json(name = "Rank") val rank: Int,
    @Json(name = "Name") val name: String,
    @Json(name = "TPoints") val totalPoints: Int,
    @Json(name = "PvPpoints") val pvpPoints: Int,
    @Json(name = "Points") val points: Int,
    @Json(name = "Seasonpoints") val seasonPoints: Int,
    @Json(name = "RewardIndex") val rewardIndex: Int,
    @Json(name = "Powers") val powers: String
)

@JsonClass(generateAdapter = true)
data class GoresLastTeammate(
    @Json(name = "finishesnumber") val finishesNumber: Int, @Json(name = "Namee") val name: String
)

@JsonClass(generateAdapter = true)
data class GoresFinishedMap(
    val count: Int,
    val quests: String,
    @Json(name = "TS") val timestamp: Date,
    @Json(name = "T") val time: Float,
    @Json(name = "Server") val server: String,
    @Json(name = "Map") val map: String

)

@JsonClass(generateAdapter = true)
data class GoresNumWrapper(
    @Json(name = "myoutput") val n: Int
)

@JsonClass(generateAdapter = true)
data class TeeSkin(
    @Json(name = "SkinName") val name: String,
    @Json(name = "SkinColorBody") val bodyColor: String,
    @Json(name = "SkinColorFeet") val feetColor: String
)

@JsonClass(generateAdapter = true)
data class GoresTimePointsWrapper(
    @Json(name = "y") val point: Int, @Json(name = "label") val time: String
)

@JsonClass(generateAdapter = true)
data class GoresPlayerData(
    val points: GoresPoints,
    // val finishedMaps
    @Json(name = "lastteammates") val lastTeammates: List<GoresLastTeammate>,
    // val rewards
    val mapsFinished: List<GoresFinishedMap>,
    @Json(name = "fin_main") val easyFinish: List<GoresNumWrapper>,
    @Json(name = "easy_maps") val easyCount: GoresNumWrapper,
    @Json(name = "fin_mn") val mainFinish: GoresNumWrapper,
    @Json(name = "main_maps") val mainCount: GoresNumWrapper,
    @Json(name = "fin_hrd") val hardFinish: GoresNumWrapper,
    @Json(name = "hard_maps") val hardCount: GoresNumWrapper,
    @Json(name = "fin_ins") val insaneFinish: GoresNumWrapper,
    @Json(name = "ins_maps") val insaneCount: GoresNumWrapper,
    @Json(name = "fin_ext") val extraFinish: GoresNumWrapper,
    @Json(name = "ext_maps") val extraCount: GoresNumWrapper,
    @Json(name = "fin_sol") val soloFinish: GoresNumWrapper,
    @Json(name = "sol_maps") val soloCount: GoresNumWrapper,
    @Json(name = "fin_mod") val modFinish: GoresNumWrapper,
    @Json(name = "mod_maps") val modCount: GoresNumWrapper,
    // val warnings
    @Json(name = "last_tee") val skin: TeeSkin,
    @Json(name = "time_wasted") val totalTime: Float,
    @Json(name = "points_over_time") val pointsOverTime: List<GoresTimePointsWrapper>
)
