package com.mcwindy.ddrhelper.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TablerowRankData(
    val index: Int, val id: String, val points: Int, val country: String, var flagRes: Int = -1
)
