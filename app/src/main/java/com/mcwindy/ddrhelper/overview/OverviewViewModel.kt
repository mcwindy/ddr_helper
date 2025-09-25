package com.mcwindy.ddrhelper.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.rewarded.RewardedAd
import com.mcwindy.ddrhelper.model.DdnetPlayerData
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.network.DdnetApi
import com.mcwindy.ddrhelper.network.RateLimitedApiClient
import com.mcwindy.ddrhelper.overview.FetchData.Companion.fetchData
import com.mcwindy.ddrhelper.store.DdnetPlayerDataCacheObject
import com.mcwindy.ddrhelper.store.SharedPreferencesUtils
import java.util.Date


class OverviewViewModel : ViewModel() {
    companion object {
        private const val TAG = "OverviewViewModel"
    }

    // The internal MutableLiveData that stores the status of the most recent request
    val playerData: MutableLiveData<Resource<DdnetPlayerData>> =
        DdnetPlayerDataCacheObject.playerData?.let {
            MutableLiveData(Resource.Success(it))
        } ?: MutableLiveData(Resource.Error("No data"))

    // The external immutable LiveData for the request status
    private val playerDataClient = RateLimitedApiClient<String>(3L)

    val mapTypes: Array<String> = arrayOf(
        "Novice",
        "Moderate",
        "Brutal",
        "Insane",
        "DDmaX.Easy",
        "DDmaX.Next",
        "DDmaX.Pro",
        "DDmaX.Nut",
        "Oldschool",
        "Solo",
        "Dummy",
        "Race"
    )

    var rewardedAd: RewardedAd? = null

    /** Call getPlayerData() on init so we can display status immediately. */
    init {
        // getPlayerData("1") { data ->
        //     Log.w(TAG, "inited" + data.points)
        // }
    }

    fun getPlayerData(name: String = SharedPreferencesUtils.playerName) {
        fetchData(
            viewModelScope,
            apiClient = playerDataClient,
            apiCall = { DdnetApi.retrofitService.getPlayerData(name) },
            onSuccess = { result ->
                result.updateTime = Date().time
                // Cache if is the same player
                if (name == SharedPreferencesUtils.playerName) {
                    DdnetPlayerDataCacheObject.playerData = result
                }
                playerData.postValue(Resource.Success(result))
            },
            onError = { e -> playerData.postValue(Resource.Error("${e.message}")) })
    }
}
