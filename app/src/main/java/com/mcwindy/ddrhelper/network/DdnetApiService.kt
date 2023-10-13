package com.mcwindy.ddrhelper.network

import com.mcwindy.ddrhelper.model.DdnetPlayerData
import retrofit2.http.GET
import retrofit2.http.Query

/** A public interface that exposes the [getPlayerData] method */
interface DdnetApiService {
    /**
     * Returns a [DdnetPlayerData] and this method can be called from a Coroutine. The @GET annotation
     * indicates that the "photos" endpoint will be requested with the GET HTTP method
     */
    @GET("/players")
    suspend fun getPlayerData(@Query("json2") json2: String): DdnetPlayerData
    // https://info.ddnet.org/info?name=
}
