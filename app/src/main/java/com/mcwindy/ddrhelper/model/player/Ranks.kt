package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ranks(val points: Int?, val rank: Int?)
