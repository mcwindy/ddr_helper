package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Points(val total: Int, val points: Int = 0, val rank: Int?)
