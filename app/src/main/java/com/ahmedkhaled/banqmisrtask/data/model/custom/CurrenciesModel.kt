package com.ahmedkhaled.banqmisrtask.data.model.custom


data class CurrenciesModel(
    val base: String,
    val date: String,
    val data: MutableList<CurrenciesItems>,
    val historical: Boolean? = null
)

data class CurrenciesItems(
    val name: String,
    val rate: Double
    )