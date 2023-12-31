package com.ahmedkhaled.banqmisrtask.data.remote

import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse
import com.ahmedkhaled.banqmisrtask.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("latest")
    suspend fun latestCurrencies(
        @Query("access_key") access_key: String = Constants.ACCESS_KEY,
        @Query("symbols") symbols: String = Constants.SYMBOLS
    ): LatestCurrenciesResponse

    @GET("{date}")
    suspend fun historicalData(
        @Path("date") date: String,
        @Query("symbols") symbols: String = Constants.SYMBOLS,
        @Query("access_key") access_key: String = Constants.ACCESS_KEY
    ): LatestCurrenciesResponse


}