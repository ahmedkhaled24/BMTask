package com.ahmedkhaled.banqmisrtask.data.repository

import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse
import com.ahmedkhaled.banqmisrtask.data.remote.ApiInterface
import com.ahmedkhaled.banqmisrtask.domain.repository.HistoricalDataApIRepo
import javax.inject.Inject

class HistoricalDataApiRepoImpl @Inject constructor(private val api: ApiInterface) :
    HistoricalDataApIRepo {

    override suspend fun historicalData(date: String): LatestCurrenciesResponse {
        return api.historicalData(date)
    }
}