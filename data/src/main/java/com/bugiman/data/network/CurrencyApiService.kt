package com.bugiman.data.network

import com.bugiman.data.model.currency.CurrencyPairResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {
    @GET("v6/{apiKey}/pair/{from}/{to}")
    suspend fun getPairRate(
        @Path("apiKey") apiKey: String,
        @Path("from") fromCurrency: String,
        @Path("to") toCurrency: String
    ): CurrencyPairResponseDto
}