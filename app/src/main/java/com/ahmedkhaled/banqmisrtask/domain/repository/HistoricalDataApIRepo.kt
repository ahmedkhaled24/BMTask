package com.ahmedkhaled.banqmisrtask.domain.repository

import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse

interface HistoricalDataApIRepo {
    suspend fun historicalData(date: String): LatestCurrenciesResponse
}