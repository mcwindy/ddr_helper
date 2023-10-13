package com.mcwindy.ddrhelper.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val GORES_BASE_URL = "https://ddnet.org"

/** Build the Moshi object with Kotlin adapter factory that Retrofit will be using. */
private val moshi = Moshi.Builder().add(DateJsonAdapter("yyyy-MM-dd HH:mm:ss")).build()

/** The Retrofit object with the Moshi converter. */
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GORES_BASE_URL).build()

/** A public Api object that exposes the lazy-initialized Retrofit service */
object GoresApi {
    val retrofitService: GoresApiService by lazy {
        retrofit.create(GoresApiService::class.java)
    }

}
