package com.mcwindy.ddrhelper.network

import com.mcwindy.ddrhelper.model.ServerData
import retrofit2.http.GET

interface StatusApiService {
    @GET("/server/list")
    suspend fun getServerList(): List<ServerData>

    @GET("/player")
    suspend fun getPlayer()
}

