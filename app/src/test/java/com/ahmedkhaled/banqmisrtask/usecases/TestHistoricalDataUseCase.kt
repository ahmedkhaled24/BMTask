package com.ahmedkhaled.banqmisrtask.usecases

import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse
import com.ahmedkhaled.banqmisrtask.data.model.response.Rates
import com.ahmedkhaled.banqmisrtask.domain.repository.HistoricalDataApIRepo
import com.ahmedkhaled.banqmisrtask.domain.usecases.HistoricalDataUseCase
import com.ahmedkhaled.banqmisrtask.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TestHistoricalDataUseCase {

    private lateinit var historicalDataUseCase: HistoricalDataUseCase
    private val repository: HistoricalDataApIRepo = mockk()

    @Before
    fun setup() {
        historicalDataUseCase = HistoricalDataUseCase(repository)
    }

    @Test
    fun `test historicalDataUseCase success`() = runBlocking {
        val fakeCurrenciesModel = LatestCurrenciesResponse(
            success = true,
            historical = true,
            timestamp = 1426512,
            base = "EUR",
            date = "2023-08-27",
            rates = Rates(1.0, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07,)
        )
        coEvery { repository.historicalData(any()) } returns fakeCurrenciesModel

        val result: Flow<Resource<CurrenciesModel>> = historicalDataUseCase.invoke("2023-08-27")
    }

    @Test
    fun `test historicalDataUseCase failure`() = runBlocking {
        val errorMessage = "An error occurred"
        coEvery { repository.historicalData(any()) } throws Exception(errorMessage)
        val result: Flow<Resource<CurrenciesModel>> = historicalDataUseCase.invoke("2023-08-27")
    }


}