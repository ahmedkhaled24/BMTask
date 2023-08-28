package com.ahmedkhaled.banqmisrtask.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.domain.usecases.LatestCurrenciesUseCase
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.LatestCurrenciesViewModel
import com.ahmedkhaled.banqmisrtask.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestLatestCurrenciesViewModel {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LatestCurrenciesViewModel
    private val latestCurrenciesUseCase: LatestCurrenciesUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = LatestCurrenciesViewModel(latestCurrenciesUseCase)
    }

    @Test
    fun `test latestCurrenciesData success`() = runBlockingTest {
        val arr: MutableList<CurrenciesItems> = ArrayList()
        arr.add(CurrenciesItems("EGP", 30.9))
        val fakeCurrenciesModel = CurrenciesModel("base", "date", arr)
        coEvery { latestCurrenciesUseCase.invoke() } returns flowOf(Resource.Success(fakeCurrenciesModel))

        viewModel.latestCurrenciesData()

        assertEquals(Resource.Success(fakeCurrenciesModel), viewModel.latestCurrenciesData.value)
    }


}