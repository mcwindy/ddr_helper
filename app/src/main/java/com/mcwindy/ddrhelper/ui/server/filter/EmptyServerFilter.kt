package com.mcwindy.ddrhelper.ui.server.filter

import com.mcwindy.ddrhelper.model.ServerData
import com.mcwindy.ddrhelper.ui.server.ServerListFilter

class EmptyServerFilter : ServerListFilter() {
    companion object {
        fun filter(serverDataList: List<ServerData>): List<ServerData> {
            return serverDataList.filter { it.numClients > 0 }
        }
    }
}
