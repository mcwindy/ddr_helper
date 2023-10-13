package com.mcwindy.ddrhelper.network

import java.time.Instant

class RateLimitedApiClient<T>(
    private val minInterval: Long
) {

    private var nextAvailableTime: Instant = Instant.ofEpochSecond(0)
    private var lastArg: T? = null

    fun availableToCall(arg: T? = null): Boolean {
        // Check rate limit
        return nextAvailableTime < Instant.now() || arg != lastArg
    }

    fun callApi() {
        nextAvailableTime = Instant.now().plusSeconds(minInterval)
    }
}