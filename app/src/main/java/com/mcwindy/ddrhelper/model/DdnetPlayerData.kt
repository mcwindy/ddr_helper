package com.mcwindy.ddrhelper.model

import com.mcwindy.ddrhelper.model.player.Activity
import com.mcwindy.ddrhelper.model.player.FavoritePartner
import com.mcwindy.ddrhelper.model.player.FirstFinish
import com.mcwindy.ddrhelper.model.player.LastFinish
import com.mcwindy.ddrhelper.model.player.Points
import com.mcwindy.ddrhelper.model.player.Ranks
import com.mcwindy.ddrhelper.model.player.Types
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/** The property names of this data class are used by Moshi to match the names of values in JSON. */
@JsonClass(generateAdapter = true)
data class DdnetPlayerData(
    val player: String,
    val points: Points,
    @param:Json(name = "team_rank") val teamRank: Ranks,
    val rank: Ranks,
    @param:Json(name = "points_last_month") val pointsLastMonth: Ranks,
    @param:Json(name = "points_last_week") val pointsLastWeek: Ranks,
    @param:Json(name = "first_finish") val firstFinish: FirstFinish,
    @param:Json(name = "last_finishes") val lastFinishes: List<LastFinish>,
    @param:Json(name = "favorite_partners") val favoritePartners: List<FavoritePartner>,
    val types: Map<String, Types>,
    val activity: List<Activity>,
    @param:Json(name = "hours_played_past_365_days") val hoursPlayedPast365Days: Int,
    var updateTime: Long = 0 // This is not in the JSON, but is added by the app
)

// "points": {"total": 31181, "points": 10583, "rank": 838}, "team_rank": {"rank": null}


// {
//     {
//         "timestamp": 1587137164.0,
//         "map": "StepByStep",
//         "time": 414.24
//     },
//     {
//         "timestamp": 1694494999.0,
//         "map": "run_Div2",
//         "time": 333.58,
//         "country": "CHN",
//         "type": "Race"
//     },
//     "xyzyx": {
//         "points": 4,
//         "total_finishes": 30977,
//         "finishes": 2,
//         "rank": 2023,
//         "time": 157.56,
//         "first_finish": 1671513232.0
//     },
//     "Dark Depth": { "points": 20, "total_finishes": 847, "finishes": 0 },
// }
