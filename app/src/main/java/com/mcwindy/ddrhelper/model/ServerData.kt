package com.mcwindy.ddrhelper.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ServerData(
    @Json(name = "server_id") val serverId: Int,
    val ip: String,
    val port: Int,
    val name: String,
    val map: String,
    @Json(name = "gametype") val gameType: String,
    val version: String,
    val password: Boolean,
    @Json(name = "server_level") val serverLevel: Int,
    val hostname: String,
    @Json(name = "master_server") val masterServer: String,
    @Json(name = "num_clients") val numClients: Int,
    @Json(name = "max_clients") val maxClients: Int,
    @Json(name = "num_players") val numPlayers: Int,
    @Json(name = "max_players") val maxPlayers: Int,
    @Json(name = "num_bot_players") val numBotPlayers: Int,
    @Json(name = "num_bot_spectators") val numBotSpectators: Int,
    @Json(name = "num_dummies") val numDummies: Int,
    @Json(name = "is_legacy") val isLegacy: Boolean,
    @Json(name = "is_multi_support") val isMultiSupport: Boolean,
    @Json(name = "first_seen") val firstSeen: Date,
    @Json(name = "last_seen") val lastSeen: Date,
    val clients: List<ClientData>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        Date(parcel.readLong()),
        Date(parcel.readLong()),
        parcel.createTypedArrayList(ClientData.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(serverId)
        parcel.writeString(ip)
        parcel.writeInt(port)
        parcel.writeString(name)
        parcel.writeString(map)
        parcel.writeString(gameType)
        parcel.writeString(version)
        parcel.writeByte(if (password) 1 else 0)
        parcel.writeInt(serverLevel)
        parcel.writeString(hostname)
        parcel.writeString(masterServer)
        parcel.writeInt(numClients)
        parcel.writeInt(maxClients)
        parcel.writeInt(numPlayers)
        parcel.writeInt(maxPlayers)
        parcel.writeInt(numBotPlayers)
        parcel.writeInt(numBotSpectators)
        parcel.writeInt(numDummies)
        parcel.writeByte(if (isLegacy) 1 else 0)
        parcel.writeByte(if (isMultiSupport) 1 else 0)
        parcel.writeLong(firstSeen.time)
        parcel.writeLong(lastSeen.time)
        parcel.writeTypedList(clients)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt("serverId", serverId)
        bundle.putString("ip", ip)
        bundle.putInt("port", port)
        bundle.putString("name", name)
        bundle.putString("map", map)
        bundle.putString("gameType", gameType)
        bundle.putString("version", version)
        bundle.putBoolean("password", password)
        bundle.putInt("serverLevel", serverLevel)
        bundle.putString("hostname", hostname)
        bundle.putString("masterServer", masterServer)
        bundle.putInt("numClients", numClients)
        bundle.putInt("maxClients", maxClients)
        bundle.putInt("numPlayers", numPlayers)
        bundle.putInt("maxPlayers", maxPlayers)
        bundle.putInt("numBotPlayers", numBotPlayers)
        bundle.putInt("numBotSpectators", numBotSpectators)
        bundle.putInt("numDummies", numDummies)
        bundle.putBoolean("isLegacy", isLegacy)
        bundle.putBoolean("isMultiSupport", isMultiSupport)
        bundle.putLong("firstSeen", firstSeen.time)
        bundle.putLong("lastSeen", lastSeen.time)
        bundle.putParcelableArrayList("clients", ArrayList(clients))
        return bundle
    }

    companion object CREATOR : Parcelable.Creator<ServerData> {
        override fun createFromParcel(parcel: Parcel): ServerData {
            return ServerData(parcel)
        }

        override fun newArray(size: Int): Array<ServerData?> {
            return arrayOfNulls(size)
        }

        fun fromBundle(bundle: Bundle): ServerData {
            return ServerData(
                serverId = bundle.getInt("serverId"),
                ip = bundle.getString("ip")!!,
                port = bundle.getInt("port"),
                name = bundle.getString("name")!!,
                map = bundle.getString("map")!!,
                gameType = bundle.getString("gameType")!!,
                version = bundle.getString("version")!!,
                password = bundle.getBoolean("password"),
                serverLevel = bundle.getInt("serverLevel"),
                hostname = bundle.getString("hostname")!!,
                masterServer = bundle.getString("masterServer")!!,
                numClients = bundle.getInt("numClients"),
                maxClients = bundle.getInt("maxClients"),
                numPlayers = bundle.getInt("numPlayers"),
                maxPlayers = bundle.getInt("maxPlayers"),
                numBotPlayers = bundle.getInt("numBotPlayers"),
                numBotSpectators = bundle.getInt("numBotSpectators"),
                numDummies = bundle.getInt("numDummies"),
                isLegacy = bundle.getBoolean("isLegacy"),
                isMultiSupport = bundle.getBoolean("isMultiSupport"),
                firstSeen = Date(bundle.getLong("firstSeen")),
                lastSeen = Date(bundle.getLong("lastSeen")),
                clients = bundle.getParcelableArrayList("clients")!!
            )
        }
    }
}
