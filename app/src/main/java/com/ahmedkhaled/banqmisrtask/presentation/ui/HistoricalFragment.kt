package com.ahmedkhaled.banqmisrtask.presentation.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.data.model.custom.DataLast3Days
import com.ahmedkhaled.banqmisrtask.data.model.custom.DataOtherCurrencies
import com.ahmedkhaled.banqmisrtask.databinding.FragmentHistoricalBinding
import com.ahmedkhaled.banqmisrtask.presentation.adapters.Last3DaysAdapter
import com.ahmedkhaled.banqmisrtask.presentation.adapters.OtherCurrenciesAdapter
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.HistoricalViewModel
import com.ahmedkhaled.banqmisrtask.utils.NumberProcessing.oneDigit
import com.ahmedkhaled.banqmisrtask.utils.Resource
import com.ahmedkhaled.banqmisrtask.utils.SetUpLineChart
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "TAGHistoricalFragment"
@AndroidEntryPoint
class HistoricalFragment : Fragment() {

    private lateinit var binding: FragmentHistoricalBinding
    private val args: HistoricalFragmentArgs by navArgs()
    private val viewModel: HistoricalViewModel by viewModels()
    private lateinit var todayDate: String
    private lateinit var last3DaysAdapter: Last3DaysAdapter
    private lateinit var otherCurrenciesAdapter: OtherCurrenciesAdapter
    private var arrDatesLast3Days: List<String> = ArrayList()
    private var arrAllDataLast3Day: MutableList<DataLast3Days> = ArrayList()
    private var arrOtherCurrencies: MutableList<DataOtherCurrencies> = ArrayList()
    private var counterCallApiLastDays = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoricalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getThreePreviousDaysAndCurrent()
        callApis()
        initResponseApiForLast3Days()
        initResponseApiForOtherCurrencies()

        //init recyclers to display data
        setUpRecyclerLast3DaysData()
        setUpRecyclerOtherCurrencies()

        binding.textViewMainCurrency.text = getString(R.string.currencyEqual, args.nameFirstCurrency)
        binding.textViewMainOtherCurrency.text = getString(R.string.currencyEqual, args.nameFirstCurrency)
    }

    private fun setUpRecyclerLast3DaysData() {
        last3DaysAdapter = Last3DaysAdapter()
        binding.recyclerViewLastDays.adapter = last3DaysAdapter
    }

    private fun setUpRecyclerOtherCurrencies() {
        otherCurrenciesAdapter = OtherCurrenciesAdapter()
        binding.recyclerViewOtherCurrencies.adapter = otherCurrenciesAdapter
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getThreePreviousDaysAndCurrent() {
        val currentDate = LocalDate.now()
        todayDate = currentDate.minusDays(0).toString()

        val oneDayAgo = currentDate.minusDays(1).toString()
        val twoDaysAgo = currentDate.minusDays(2).toString()
        val threeDaysAgo = currentDate.minusDays(3).toString()

        arrDatesLast3Days = listOf(oneDayAgo, twoDaysAgo, threeDaysAgo)
    }

    private fun callApis() {
        viewModel.historicalData(arrDatesLast3Days[counterCallApiLastDays])
        viewModel.otherCurrenciesData(todayDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initResponseApiForLast3Days() {
        viewModel.historicalData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    processingOnResponseLast3DaysData(it.data!!.date, it.data.data)
                    counterCallApiLastDays +=1
                    if (counterCallApiLastDays<arrDatesLast3Days.size){
                        viewModel.historicalData(arrDatesLast3Days[counterCallApiLastDays])
                    }
                }

                is Resource.Error -> {
                    Log.d(TAG, "initResponseApiForLast3Days Error: ${it.message!!}")
                    showToast(it.message)
                }
            }
        }
    }

    private fun initResponseApiForOtherCurrencies() {
        viewModel.otherCurrenciesData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    processingOtherCurrenciesData(it.data!!.data)
                }

                is Resource.Error -> {
                    Log.d(TAG, "initResponseApiForOtherCurrencies Error: ${it.message!!}")
                }
            }
        }
    }

    private fun processingOtherCurrenciesData(data: MutableList<CurrenciesItems>) {
        for (i in 0 until data.size)
            if (i != args.positionFirstItem)
                arrOtherCurrencies.add(DataOtherCurrencies(data[i].name, oneDigit(data[i].rate / data[args.positionFirstItem].rate)))

        otherCurrenciesAdapter.differ.submitList(arrOtherCurrencies)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processingOnResponseLast3DaysData(date: String, data: MutableList<CurrenciesItems>) {
        val result = data[args.positionSecondItem].rate / data[args.positionFirstItem].rate
        val isItemInArray = arrAllDataLast3Day.any { it.date == date }
        if (!isItemInArray)
            arrAllDataLast3Day.add(DataLast3Days(date, result.toString(), data[args.positionSecondItem].name))

        val dataLast3DaysSorted = sortDatesDescending(arrAllDataLast3Day)
        last3DaysAdapter.differ.submitList(dataLast3DaysSorted)

        SetUpLineChart.setChart(requireContext(), binding.idChart, dataLast3DaysSorted)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sortDatesDescending(dates: MutableList<DataLast3Days>): List<DataLast3Days> {
        return dates.sortedByDescending { LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}



