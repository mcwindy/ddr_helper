package com.mcwindy.ddrhelper.overview

import android.util.Log
import com.mcwindy.ddrhelper.network.RateLimitedApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FetchData {
    companion object {
        private const val TAG = "FetchData"
        fun <T> fetchData(
            scope: CoroutineScope,
            apiClient: RateLimitedApiClient<*>,
            apiCall: suspend () -> T,
            onSuccess: (T) -> Unit,
            onError: (Exception) -> Unit
        ) {
            if (apiClient.availableToCall()) {
                apiClient.callApi()
                scope.launch {
                    try {
                        val result = apiCall()
                        onSuccess(result)
                    } catch (e: Exception) {
                        Log.w(TAG, "Failed ${e.message}")
                        onError(e)
                    }
                }
            } else {
                Log.i(TAG, "Success2: fake fetching")
            }
        }
    }

}