package com.ahmedkhaled.banqmisrtask.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhaled.banqmisrtask.data.model.custom.CustomCurrenciesModel
import com.ahmedkhaled.banqmisrtask.domain.usecases.LatestCurrenciesUseCase
import com.ahmedkhaled.banqmisrtask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestCurrenciesViewModel @Inject constructor(private val latestCurUseCase: LatestCurrenciesUseCase)
    : ViewModel() {

    private val _latestCurrenciesState: MutableLiveData<Resource<CustomCurrenciesModel>> =
        MutableLiveData()
    val latestCurrenciesData: LiveData<Resource<CustomCurrenciesModel>> = _latestCurrenciesState


    init {
        latestCurrenciesData()
    }

    private fun latestCurrenciesData() {
        viewModelScope.launch {
            latestCurUseCase().collect {
                _latestCurrenciesState.postValue(it)
            }
        }
    }
}