package com.ahmedkhaled.banqmisrtask.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesModel
import com.ahmedkhaled.banqmisrtask.domain.usecases.LatestCurrenciesUseCase
import com.ahmedkhaled.banqmisrtask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestCurrenciesViewModel @Inject constructor(private val latestCurUseCase: LatestCurrenciesUseCase)
    : ViewModel() {

    var savePositionSpinnerItemOne: Int = 0
    var savePositionSpinnerItemTwo: Int = 1

    private val _latestCurrenciesState: MutableLiveData<Resource<CurrenciesModel>> =
        MutableLiveData()
    val latestCurrenciesData: LiveData<Resource<CurrenciesModel>> = _latestCurrenciesState


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

//    private val _selectedItem = MutableLiveData<CurrenciesItems>()
//    val selectedItem: LiveData<CurrenciesItems>
//        get() = _selectedItem
//
//    fun onItemSelected(item: CurrenciesItems) {
//        _selectedItem.value = item
//        // Perform business logic based on the selected item
//    }

}