package com.mcwindy.ddrhelper.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DdnetRankData(
    var point: List<TablerowRankData> = emptyList(),
    var point365: List<TablerowRankData> = emptyList(),
    var teamRank: List<TablerowRankData> = emptyList(),
    var rank: List<TablerowRankData> = emptyList(),
    var point30: List<TablerowRankData> = emptyList(),
    var point7: List<TablerowRankData> = emptyList(),
    var updateTime: Long = 0
)
