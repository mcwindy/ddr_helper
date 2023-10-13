package com.mcwindy.ddrhelper.ui.server.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.model.ServerData

enum class FilterOption(val stringResId: Int) {
    IGNORE_EMPTY_SERVERS(R.string.ignore_empty_servers) {
        override fun filter(list: List<ServerData>): List<ServerData> {
            return list.filter { it.numClients > 0 }
        }
    },
    SHOW_GORES_SERVERS(R.string.show_gores_servers) {
        override fun filter(list: List<ServerData>): List<ServerData> {
            return list.filter {
                it.name.lowercase().contains("gores")
            }
        }
    },
    SHOW_DDNET_SERVERS(R.string.show_ddnet_servers) {
        override fun filter(list: List<ServerData>): List<ServerData> {
            return list.filter {
                it.name.lowercase().contains("ddnet")
            }
        }
    },
    SHOW_CHN_SERVERS(R.string.show_chn_servers) {
        override fun filter(list: List<ServerData>): List<ServerData> {
            val filterRegex = Regex("中|CHN|chn")
            return list.filter { it.name.contains(filterRegex) }
        }
    },
    ;

    abstract fun filter(list: List<ServerData>): List<ServerData>
}

enum class SortOption(val stringResId: Int) {
    INCREASE_NUM_CLIENTS(R.string.increase_num_clients) {
        override fun compare(server1: ServerData, server2: ServerData): Int {
            return server2.numClients - server1.numClients
        }
    },
    DECREASE_NUM_CLIENTS(R.string.decrease_num_clients) {
        override fun compare(server1: ServerData, server2: ServerData): Int {
            return server1.numClients - server2.numClients
        }
    };

    abstract fun compare(server1: ServerData, server2: ServerData): Int
}

enum class ClassifyOption(val stringResId: Int) {
    NO_CLASSIFY(R.string.no_classify) {
        override fun map(server: ServerData): String {
            return "All"
        }
    },
    BY_IP(R.string.by_ip) {
        override fun map(server: ServerData): String {
            return server.ip
        }
    },
    BY_VERSION(R.string.by_version) {
        override fun map(server: ServerData): String {
            return server.version
        }
    };

    abstract fun map(server: ServerData): String
}

class FilterViewModel : ViewModel() {
    var filterList = MutableLiveData<List<FilterOption>>(emptyList())
    val filterOptions = listOf(
        FilterOption.IGNORE_EMPTY_SERVERS,
        FilterOption.SHOW_GORES_SERVERS,
        FilterOption.SHOW_DDNET_SERVERS,
        FilterOption.SHOW_CHN_SERVERS
    )

    var sortedBy = MutableLiveData<SortOption>(SortOption.DECREASE_NUM_CLIENTS)
    val sortOptions = listOf(SortOption.DECREASE_NUM_CLIENTS, SortOption.INCREASE_NUM_CLIENTS)
    var classifyBy = MutableLiveData<ClassifyOption>(ClassifyOption.BY_VERSION)
    val classifyOptions =
        listOf(ClassifyOption.NO_CLASSIFY, ClassifyOption.BY_IP, ClassifyOption.BY_VERSION)
    val isViewFinished = MutableLiveData<Boolean>()
}
