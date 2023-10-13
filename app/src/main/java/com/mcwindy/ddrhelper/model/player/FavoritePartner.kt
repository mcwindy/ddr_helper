package com.mcwindy.ddrhelper.model.player

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoritePartner(val name: String, val finishes: Int)
