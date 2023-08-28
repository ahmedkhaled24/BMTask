package com.ahmedkhaled.banqmisrtask.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.domain.usecases.HistoricalDataUseCase
import com.ahmedkhaled.banqmisrtask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricalViewModel @Inject constructor(private val historicalDataUseCase: HistoricalDataUseCase) :
    ViewModel() {

    private val _historicalDataState: MutableLiveData<Resource<CurrenciesModel>> = MutableLiveData()
    val historicalData: LiveData<Resource<CurrenciesModel>> = _historicalDataState

    fun historicalData(date: String) {
        viewModelScope.launch {
            historicalDataUseCase(date).collect {
                _historicalDataState.postValue(it)
            }
        }
    }


    private val _otherCurrenciesState: MutableLiveData<Resource<CurrenciesModel>> = MutableLiveData()
    val otherCurrenciesData: LiveData<Resource<CurrenciesModel>> = _otherCurrenciesState

    fun otherCurrenciesData(todayDate: String) {
        viewModelScope.launch {
            historicalDataUseCase(todayDate).collect {
                _otherCurrenciesState.postValue(it)
            }
        }
    }
}