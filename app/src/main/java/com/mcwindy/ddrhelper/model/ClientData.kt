package com.mcwindy.ddrhelper.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ClientData(
    val name: String,
    val clan: String?,
    val country: Int,
    val score: Int,
    @Json(name = "is_player") val isPlayer: Boolean,
    @Json(name = "is_bot") val isBot: Boolean,
    @Json(name = "is_dummy") val isDummy: Boolean,
    @Json(name = "first_seen") val firstSeen: Date,
    @Json(name = "last_seen") val lastSeen: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(clan)
        parcel.writeInt(country)
        parcel.writeInt(score)
        parcel.writeByte(if (isPlayer) 1 else 0)
        parcel.writeByte(if (isBot) 1 else 0)
        parcel.writeByte(if (isDummy) 1 else 0)
        parcel.writeLong(firstSeen.time)
        parcel.writeLong(lastSeen.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClientData> {
        override fun createFromParcel(parcel: Parcel): ClientData {
            return ClientData(parcel)
        }

        override fun newArray(size: Int): Array<ClientData?> {
            return arrayOfNulls(size)
        }
    }
}