package com.ahmedkhaled.banqmisrtask.usecases

import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse
import com.ahmedkhaled.banqmisrtask.data.model.response.Rates
import com.ahmedkhaled.banqmisrtask.domain.repository.CurrenciesApIRepo
import com.ahmedkhaled.banqmisrtask.domain.usecases.LatestCurrenciesUseCase
import com.ahmedkhaled.banqmisrtask.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class TestLatestCurrenciesUseCase {

    private lateinit var latestCurrenciesUseCase: LatestCurrenciesUseCase
    private val repository: CurrenciesApIRepo = mockk()

    @Before
    fun setup() {
        latestCurrenciesUseCase = LatestCurrenciesUseCase(repository)
    }

    @Test
    fun `test latestCurrenciesUseCase success`() = runBlocking {
        val fakeCurrenciesModel = LatestCurrenciesResponse(
            success = true,
            timestamp = 1426512,
            base = "EUR",
            date = "2023-08-27",
            rates = Rates(1.0, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07, 1.07,)
        )
        coEvery { repository.latestCurrencies() } returns fakeCurrenciesModel

        val result: Flow<Resource<CurrenciesModel>> = latestCurrenciesUseCase.invoke()
    }

    @Test
    fun `test latestCurrenciesUseCase failure`() = runBlocking {
        val errorMessage = "An error occurred"
        coEvery { repository.latestCurrencies() } throws Exception(errorMessage)
        val result: Flow<Resource<CurrenciesModel>> = latestCurrenciesUseCase.invoke()
    }


}