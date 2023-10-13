package com.mcwindy.ddrhelper.ui.server

import com.mcwindy.ddrhelper.model.ServerData

abstract class ServerListFilter {
    companion object {
        fun filter(serverDataList: List<ServerData>): List<ServerData>{
            TODO("Implement me")
        }
    }
}
