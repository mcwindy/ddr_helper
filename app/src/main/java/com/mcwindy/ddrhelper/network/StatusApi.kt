package com.mcwindy.ddrhelper.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val STATUS_BASE_URL: String = "https://api.status.tw"

/** Build the Moshi object with Kotlin adapter factory that Retrofit will be using. */
private val moshi = Moshi.Builder().add(DateJsonAdapter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).build()

/** The Retrofit object with the Moshi converter. */
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(STATUS_BASE_URL).build()

/** A public Api object that exposes the lazy-initialized Retrofit service */
object StatusApi {
    val retrofitService: StatusApiService by lazy {
        retrofit.create(StatusApiService::class.java)
    }

}
