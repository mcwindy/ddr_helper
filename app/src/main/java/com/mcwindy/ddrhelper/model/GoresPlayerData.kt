package com.mcwindy.ddrhelper.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class GoresPoints(
    @param:Json(name = "Rank") val rank: Int,
    @param:Json(name = "Name") val name: String,
    @param:Json(name = "TPoints") val totalPoints: Int,
    @param:Json(name = "PvPpoints") val pvpPoints: Int,
    @param:Json(name = "Points") val points: Int,
    @param:Json(name = "Seasonpoints") val seasonPoints: Int,
    @param:Json(name = "RewardIndex") val rewardIndex: Int,
    @param:Json(name = "Powers") val powers: String
)

@JsonClass(generateAdapter = true)
data class GoresLastTeammate(
    @param:Json(name = "finishesnumber") val finishesNumber: Int,
    @param:Json(name = "Namee") val name: String
)

@JsonClass(generateAdapter = true)
data class GoresFinishedMap(
    val count: Int,
    val quests: String,
    @param:Json(name = "TS") val timestamp: Date,
    @param:Json(name = "T") val time: Float,
    @param:Json(name = "Server") val server: String,
    @param:Json(name = "Map") val map: String
)

@JsonClass(generateAdapter = true)
data class GoresNumWrapper(
    @param:Json(name = "myoutput") val n: Int
)

@JsonClass(generateAdapter = true)
data class TeeSkin(
    @param:Json(name = "SkinName") val name: String,
    @param:Json(name = "SkinColorBody") val bodyColor: String,
    @param:Json(name = "SkinColorFeet") val feetColor: String
)

@JsonClass(generateAdapter = true)
data class GoresTimePointsWrapper(
    @param:Json(name = "y") val point: Int, @param:Json(name = "label") val time: String
)

@JsonClass(generateAdapter = true)
data class GoresPlayerData(
    val points: GoresPoints,
    // val finishedMaps
    @param:Json(name = "lastteammates") val lastTeammates: List<GoresLastTeammate>,
    // val rewards
    val mapsFinished: List<GoresFinishedMap>,
    @param:Json(name = "fin_main") val easyFinish: List<GoresNumWrapper>,
    @param:Json(name = "easy_maps") val easyCount: GoresNumWrapper,
    @param:Json(name = "fin_mn") val mainFinish: GoresNumWrapper,
    @param:Json(name = "main_maps") val mainCount: GoresNumWrapper,
    @param:Json(name = "fin_hrd") val hardFinish: GoresNumWrapper,
    @param:Json(name = "hard_maps") val hardCount: GoresNumWrapper,
    @param:Json(name = "fin_ins") val insaneFinish: GoresNumWrapper,
    @param:Json(name = "ins_maps") val insaneCount: GoresNumWrapper,
    @param:Json(name = "fin_ext") val extraFinish: GoresNumWrapper,
    @param:Json(name = "ext_maps") val extraCount: GoresNumWrapper,
    @param:Json(name = "fin_sol") val soloFinish: GoresNumWrapper,
    @param:Json(name = "sol_maps") val soloCount: GoresNumWrapper,
    @param:Json(name = "fin_mod") val modFinish: GoresNumWrapper,
    @param:Json(name = "mod_maps") val modCount: GoresNumWrapper,
    // val warnings
    @param:Json(name = "last_tee") val skin: TeeSkin,
    @param:Json(name = "time_wasted") val totalTime: Float,
    @param:Json(name = "points_over_time") val pointsOverTime: List<GoresTimePointsWrapper>
)
