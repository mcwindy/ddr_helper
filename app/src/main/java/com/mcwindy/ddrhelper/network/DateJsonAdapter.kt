package com.mcwindy.ddrhelper.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateJsonAdapter(pattern: String) {
    private val dateFormat = SimpleDateFormat(pattern, Locale.CHINA)

    @ToJson
    fun toJson(date: Date): String {
        return dateFormat.format(date)
    }

    @FromJson
    fun fromJson(dateString: String): Date {
        return dateFormat.parse(dateString)!!
    }
}
