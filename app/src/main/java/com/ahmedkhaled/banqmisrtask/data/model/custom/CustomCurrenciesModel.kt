package com.ahmedkhaled.banqmisrtask.data.model.custom

import java.io.Serializable

data class CustomCurrenciesModel(
    val base: String,
    val date: String,
    var rates: MutableList<Double>,
    var names: CurrenciesNames? = null
) : Serializable {

    data class CurrenciesRate(
        var rate: Double
    ) : Serializable {}

    data class CurrenciesNames(
        var nameEUR: String = "EUR",
        var nameUSD: String = "USD",
        var nameCAD: String = "CAD",
        var nameAED: String = "AED",
        var nameEGP: String = "EGP",
        var nameKWD: String = "KWD",
        var nameRUB: String = "RUB",
        var nameGBP: String = "GBP",
        var nameCNY: String = "CNY",
        var nameAUD: String = "AUD"
    ) : Serializable {}
}