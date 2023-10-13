package com.mcwindy.ddrhelper.ui.server

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.model.ServerData
import com.mcwindy.ddrhelper.network.RateLimitedApiClient
import com.mcwindy.ddrhelper.network.StatusApi
import kotlinx.coroutines.launch

class ServerViewModel : ViewModel() {
    companion object {
        private const val TAG = "ServerViewModel"
    }

    var serverData = MutableLiveData<Resource<List<ServerData>>>(Resource.Loading())
    private val serverDataClient = RateLimitedApiClient<Any>(10L)

    fun getServerData() {
        if (serverDataClient.availableToCall()) {
            Log.i(TAG, "Success1: real fetching")
            serverDataClient.callApi()
            serverData.value = Resource.Loading()
            viewModelScope.launch {
                try {
                    val result = StatusApi.retrofitService.getServerList()
                    serverData.postValue(Resource.Success(result))
                } catch (e: Exception) {
                    Log.w(TAG, "Failed ${e.message}")
                    serverData.postValue(Resource.Error("Failed to fetch server data. ${e.message}"))
                }
            }
        } else {
            Log.i(TAG, "Success2: fake fetching")
        }
    }
}