package com.bugiman.data.model.currency

import com.google.gson.annotations.SerializedName

data class CurrencyPairResponseDto(
    @SerializedName("result")
    val result: String,

    @SerializedName("base_code")
    val baseCode: String, // Код исходной валюты, например "USD"

    @SerializedName("target_code")
    val targetCode: String, // Код целевой валюты, например "RUB"

    @SerializedName("conversion_rate")
    val conversionRate: Double, // Тот самый курс (например, 91.50)

    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Long // Время последнего обновления (для кэширования)
)
