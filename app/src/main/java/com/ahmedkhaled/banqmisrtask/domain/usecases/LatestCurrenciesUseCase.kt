package com.ahmedkhaled.banqmisrtask.domain.usecases

import android.util.Log
import com.ahmedkhaled.banqmisrtask.data.model.custom.CustomCurrenciesModel
import com.ahmedkhaled.banqmisrtask.data.model.response.LatestCurrenciesResponse
import com.ahmedkhaled.banqmisrtask.data.model.response.Rates
import com.ahmedkhaled.banqmisrtask.domain.repository.CurrenciesApIRepo
import com.ahmedkhaled.banqmisrtask.utils.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "TAGLatestCur"
class LatestCurrenciesUseCase @Inject constructor(private val repository: CurrenciesApIRepo) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val response = repository.latestCurrencies()
            Log.d(TAG, "invoke: ${response.rates.USD}")
            if (response.success){
                emit(Resource.Success(data = fillLatestCurrenciesResponse(response)))
            } else
                emit(Resource.Error("Something went wrong"))
        } catch (e: HttpException) {
            Log.d(TAG, "error HttpException: ${e.message}")
            Log.d(TAG, "error HttpException code: ${e.code()}")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.d(TAG, "error IOException: ${e.message}")
            emit(Resource.Error("Couldn't reach server. Please check your internet connection"))
        }
    }

    private fun fillLatestCurrenciesResponse(response: LatestCurrenciesResponse): CustomCurrenciesModel {
        return CustomCurrenciesModel(
            response.base,
            response.date,
            fillCurrencyRates(response.rates)
        )
    }

    private fun fillCurrencyRates(rates: Rates): MutableList<Double> {
        val arr: MutableList<Double> = ArrayList()
        arr.add(rates.EUR)
        arr.add(rates.USD)
        arr.add(rates.CAD)
        arr.add(rates.AED)
        arr.add(rates.EGP)
        arr.add(rates.KWD)
        arr.add(rates.RUB)
        arr.add(rates.GBP)
        arr.add(rates.CNY)
        arr.add(rates.AUD)
        return arr
    }

}