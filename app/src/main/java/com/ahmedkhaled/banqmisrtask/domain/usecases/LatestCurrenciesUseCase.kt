package com.ahmedkhaled.banqmisrtask.domain.usecases

import android.util.Log
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesNames
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
//                emit(Resource.Success(data = fillLatestCurrenciesResponse(response)))
                emit(Resource.Success(data = fillCurrenciesData(response)))
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

    private fun fillCurrenciesData(response: LatestCurrenciesResponse): CurrenciesModel {
        return CurrenciesModel(response.base, response.date, fillRates(response.rates))
    }

    private fun fillRates(rates: Rates): MutableList<CurrenciesItems> {
        val arr: MutableList<CurrenciesItems> = ArrayList()
        arr.add(CurrenciesItems(CurrenciesNames().nameEUR, rates.EUR))
        arr.add(CurrenciesItems(CurrenciesNames().nameUSD, rates.USD))
        arr.add(CurrenciesItems(CurrenciesNames().nameCAD, rates.CAD))
        arr.add(CurrenciesItems(CurrenciesNames().nameAED, rates.AED))
        arr.add(CurrenciesItems(CurrenciesNames().nameEGP, rates.EGP))
        arr.add(CurrenciesItems(CurrenciesNames().nameKWD, rates.KWD))
        arr.add(CurrenciesItems(CurrenciesNames().nameRUB, rates.RUB))
        arr.add(CurrenciesItems(CurrenciesNames().nameGBP, rates.GBP))
        arr.add(CurrenciesItems(CurrenciesNames().nameCNY, rates.CNY))
        arr.add(CurrenciesItems(CurrenciesNames().nameAUD, rates.AUD))

        return arr
    }

//    private fun fillLatestCurrenciesResponse(response: LatestCurrenciesResponse): CustomCurrenciesModel {
//        return CustomCurrenciesModel(
//            response.base,
//            response.date,
//            fillCurrencyRates(response.rates),
//            fillCurrencyNames()
//        )
//    }
//
//    private fun fillCurrencyNames(): MutableList<String> {
//        val arr: MutableList<String> = ArrayList()
//        arr.add(CurrenciesNames().nameEUR)
//        arr.add(CurrenciesNames().nameUSD)
//        arr.add(CurrenciesNames().nameCAD)
//        arr.add(CurrenciesNames().nameAED)
//        arr.add(CurrenciesNames().nameEGP)
//        arr.add(CurrenciesNames().nameKWD)
//        arr.add(CurrenciesNames().nameRUB)
//        arr.add(CurrenciesNames().nameGBP)
//        arr.add(CurrenciesNames().nameCNY)
//        arr.add(CurrenciesNames().nameAUD)
//
//        return arr
//    }
//
//    private fun fillCurrencyRates(rates: Rates): MutableList<Double> {
//        val arr: MutableList<Double> = ArrayList()
//        arr.add(rates.EUR)
//        arr.add(rates.USD)
//        arr.add(rates.CAD)
//        arr.add(rates.AED)
//        arr.add(rates.EGP)
//        arr.add(rates.KWD)
//        arr.add(rates.RUB)
//        arr.add(rates.GBP)
//        arr.add(rates.CNY)
//        arr.add(rates.AUD)
//        return arr
//    }

}