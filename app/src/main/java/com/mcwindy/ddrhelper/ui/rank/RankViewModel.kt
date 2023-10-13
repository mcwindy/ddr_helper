package com.mcwindy.ddrhelper.ui.rank

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mcwindy.ddrhelper.Flags
import com.mcwindy.ddrhelper.R
import com.mcwindy.ddrhelper.model.DdnetRankData
import com.mcwindy.ddrhelper.model.Resource
import com.mcwindy.ddrhelper.network.DdnetApi
import com.mcwindy.ddrhelper.network.RateLimitedApiClient
import com.mcwindy.ddrhelper.overview.FetchData
import com.mcwindy.ddrhelper.store.FileStore
import java.util.Date

class RankViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is rank Fragment\nhello from the dark side"
    }
    val text: LiveData<String> = _text
    lateinit var rankData: FileStore<Resource<DdnetRankData>>

    private val rankDataClient = RateLimitedApiClient<Any>(30L)
    fun getRankData(activity: FragmentActivity) {
        FetchData.fetchData(viewModelScope,
            apiClient = rankDataClient,
            apiCall = { DdnetApi.getRankData(viewModelScope) },
            onSuccess = { result ->
                if (result == null) {
                    Toast.makeText(
                        activity, activity.getString(R.string.network_error), Toast.LENGTH_SHORT
                    ).show()
                    return@fetchData
                }
                for (row in result.point) {
                    row.flagRes = Flags.getFlag(row.country)
                }
                for (row in result.rank) {
                    row.flagRes = Flags.getFlag(row.country)
                }
                for (row in result.teamRank) {
                    row.flagRes = Flags.getFlag(row.country)
                }
                // TODO more data.points365 etc.

                // Display raw response
                val rankViewModel = ViewModelProvider(activity)[RankViewModel::class.java]
                result.updateTime = Date().time
                rankViewModel.rankData.postValue(Resource.Success(result))
                // rankViewModel.rankData.value!!.data!!.updateTime = formatter.format(Date())
            },
            onError = { e ->
                val rankViewModel = ViewModelProvider(activity)[RankViewModel::class.java]
                rankViewModel.rankData.postValue(Resource.Error("${e.message}"))
            })
    }
}
