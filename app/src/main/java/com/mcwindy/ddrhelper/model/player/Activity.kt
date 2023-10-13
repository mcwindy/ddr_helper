package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Activity(val date: String, @Json(name = "hours_played") val hoursPlayed: Int)
