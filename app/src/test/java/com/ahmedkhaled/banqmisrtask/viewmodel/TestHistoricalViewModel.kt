package com.ahmedkhaled.banqmisrtask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.domain.usecases.HistoricalDataUseCase
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.HistoricalViewModel
import com.ahmedkhaled.banqmisrtask.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestHistoricalViewModel {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HistoricalViewModel
    private val historicalDataUseCase: HistoricalDataUseCase = mockk()

    @Before
    fun setup() {
        viewModel = HistoricalViewModel(historicalDataUseCase)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test historicalData success`() = runBlockingTest {
        val arr: MutableList<CurrenciesItems> = ArrayList()
        arr.add(CurrenciesItems("EGP", 30.9))
        val fakeCurrenciesModel = CurrenciesModel("base", "date", arr)
        coEvery { historicalDataUseCase.invoke(any()) } returns flowOf(Resource.Success(fakeCurrenciesModel))
        val observer: Observer<Resource<CurrenciesModel>> = mockk(relaxed = true)
        viewModel.historicalData.observeForever(observer)
        viewModel.historicalData("2023-08-27")
        verify { observer.onChanged(match { it is Resource.Success && it.data == fakeCurrenciesModel }) }
    }


    @Test
    fun `test otherCurrenciesData success`() = runBlockingTest {
        val arr: MutableList<CurrenciesItems> = ArrayList()
        arr.add(CurrenciesItems("EGP", 30.9))
        val fakeCurrenciesModel = CurrenciesModel("base", "date", arr)
        coEvery { historicalDataUseCase.invoke(any()) } returns flowOf(Resource.Success(fakeCurrenciesModel))
        val observer: Observer<Resource<CurrenciesModel>> = mockk(relaxed = true)
        viewModel.otherCurrenciesData.observeForever(observer)
        viewModel.otherCurrenciesData("2023-08-27")
        verify { observer.onChanged(Resource.Success(fakeCurrenciesModel)) }
    }

}