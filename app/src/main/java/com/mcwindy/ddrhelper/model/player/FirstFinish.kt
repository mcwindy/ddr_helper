package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FirstFinish(val timestamp: Double, val map: String, val time: Double)
