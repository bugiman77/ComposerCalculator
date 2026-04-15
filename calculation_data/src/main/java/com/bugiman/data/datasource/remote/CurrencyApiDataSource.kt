package com.bugiman.data.datasource.remote

import com.bugiman.data.BuildConfig
import com.bugiman.data.network.CurrencyApiService

class CurrencyApiDataSource(
    private val apiService: CurrencyApiService
) {
    private val apiKey = BuildConfig.CURRENCY_API_KEY

    suspend fun fetchPairRate(from: String, to: String): Double {
        return try {
            val response = apiService.getPairRate(
                apiKey = apiKey,
                fromCurrency = from,
                toCurrency = to
            )

            response.conversionRate
        } catch (e: Exception) {
            0.0
        }
    }
}