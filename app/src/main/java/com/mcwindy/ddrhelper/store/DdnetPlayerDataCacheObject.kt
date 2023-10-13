package com.mcwindy.ddrhelper.store

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.mcwindy.ddrhelper.model.DdnetPlayerData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object DdnetPlayerDataCacheObject {
    private lateinit var sharedPref: SharedPreferences
    private var moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<DdnetPlayerData> = moshi.adapter(DdnetPlayerData::class.java)

    var playerData: DdnetPlayerData?
        get() = sharedPref.getString("last_player_data", null)?.let { // TODO
            adapter.fromJson(it)
        }
        set(value) {
            sharedPref.edit().putString("last_player_data", adapter.toJson(value)).apply() // TODO
        }

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("playerCache", Context.MODE_PRIVATE)
    }

}