package com.mcwindy.ddrhelper.network

import com.mcwindy.ddrhelper.model.GoresPlayerData
import retrofit2.http.Body
import retrofit2.http.POST

interface GoresApiService {
    @POST("/api.php")
    suspend fun getPlayerData(@Body body: GoresQueryBody): GoresPlayerData
}

data class GoresQueryBody(val name: String) {
    val type: String = "players"
    val player: String = name
}
