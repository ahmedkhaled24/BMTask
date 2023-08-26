package com.ahmedkhaled.banqmisrtask.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LatestCurrenciesResponse(
    @Expose
    @SerializedName("success")
    val success: Boolean,
    @Expose
    @SerializedName("historical")
    val historical: Boolean? = null,
    @Expose
    @SerializedName("timestamp")
    val timestamp: Int,
    @Expose
    @SerializedName("base")
    val base: String,
    @Expose
    @SerializedName("date")
    val date: String,
    @Expose
    @SerializedName("rates")
    val rates: Rates
)

data class Rates(
    @Expose
    @SerializedName("EUR")
    val EUR: Double,
    @Expose
    @SerializedName("USD")
    val USD: Double,
    @Expose
    @SerializedName("CAD")
    val CAD: Double,
    @Expose
    @SerializedName("AED")
    val AED: Double,
    @Expose
    @SerializedName("EGP")
    val EGP: Double,
    @Expose
    @SerializedName("KWD")
    val KWD: Double,
    @Expose
    @SerializedName("RUB")
    val RUB: Double,
    @Expose
    @SerializedName("GBP")
    val GBP: Double,
    @Expose
    @SerializedName("CHF")
    val CHF: Double,
    @Expose
    @SerializedName("CNY")
    val CNY: Double,
    @Expose
    @SerializedName("AUD")
    val AUD: Double
)