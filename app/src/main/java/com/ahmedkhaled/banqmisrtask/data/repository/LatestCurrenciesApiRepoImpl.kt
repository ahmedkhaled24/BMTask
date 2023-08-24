package com.ahmedkhaled.banqmisrtask.data.repository

import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse
import com.ahmedkhaled.banqmisrtask.data.remote.ApiInterface
import com.ahmedkhaled.banqmisrtask.domain.repository.CurrenciesApIRepo
import javax.inject.Inject

class LatestCurrenciesApiRepoImpl @Inject constructor(private val api: ApiInterface) : CurrenciesApIRepo {

    override suspend fun latestCurrencies(): LatestCurrenciesResponse {
        return api.latestCurrencies()
    }
}