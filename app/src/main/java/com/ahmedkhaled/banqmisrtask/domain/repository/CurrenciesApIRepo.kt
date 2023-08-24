package com.ahmedkhaled.banqmisrtask.domain.repository

import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse

interface CurrenciesApIRepo {
    suspend fun latestCurrencies(): LatestCurrenciesResponse
}