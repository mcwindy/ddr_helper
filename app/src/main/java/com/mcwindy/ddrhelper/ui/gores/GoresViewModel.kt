package com.mcwindy.ddrhelper.ui.gores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcwindy.ddrhelper.model.GoresPlayerData
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.network.GoresApi
import com.mcwindy.ddrhelper.network.GoresQueryBody
import com.mcwindy.ddrhelper.network.RateLimitedApiClient
import com.mcwindy.ddrhelper.overview.FetchData.Companion.fetchData

class GoresViewModel : ViewModel() {
    companion object {
        private const val TAG = "GoresViewModel"
    }

    var goresData = MutableLiveData<Resource<GoresPlayerData>>()
    private val goresDataClient = RateLimitedApiClient<String>(30L)

    fun getGoresData(name: String) {
        fetchData(
            viewModelScope,
            apiClient = goresDataClient,
            apiCall = { GoresApi.retrofitService.getPlayerData(GoresQueryBody(name)) },
            onSuccess = { result ->
                goresData.postValue(Resource.Success(result))
            },
            onError = { e ->
                goresData.postValue(
                    Resource.Error(
                        "Failed to fetch data: ${e.message}", null
                    )
                )
            })
    }
}
