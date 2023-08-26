package com.ahmedkhaled.banqmisrtask.presentation.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.ahmedkhaled.banqmisrtask.databinding.FragmentHistoricalBinding
import com.ahmedkhaled.banqmisrtask.presentation.adapters.Last3DaysAdapter
import com.ahmedkhaled.banqmisrtask.presentation.viewmodels.HistoricalViewModel
import com.ahmedkhaled.banqmisrtask.utils.NumberProcessing
import com.ahmedkhaled.banqmisrtask.utils.Resource
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "TAGHistoricalFragment"
@AndroidEntryPoint
class HistoricalFragment : Fragment() {

    private lateinit var binding: FragmentHistoricalBinding
    private val viewModel: HistoricalViewModel by viewModels()
    private var arr3days: List<String> = ArrayList()
    private var todayDate: String = ""
    private val args: HistoricalFragmentArgs by navArgs()
    private lateinit var last3DaysAdapter: Last3DaysAdapter
    private var arrLast3Day: MutableList<DataLast3Days> = ArrayList()
    private var counterCallApiLastDays = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoricalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getThreePreviousDaysAndCurrent()
        callApiForGetLast3Days()
        initResponseApi()
        //init recycler to display data
        setUpRecyclerLast3DaysData()

        binding.textViewMainCurrency.text = getString(R.string.currencyEqual, args.nameFirstCurrency)
    }

    private fun setUpRecyclerLast3DaysData() {
        last3DaysAdapter = Last3DaysAdapter()
        binding.recyclerViewLastDays.adapter = last3DaysAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getThreePreviousDaysAndCurrent() {
        val currentDate = LocalDate.now()
        todayDate = currentDate.minusDays(0).toString()

        val oneDayAgo = currentDate.minusDays(1).toString()
        val twoDaysAgo = currentDate.minusDays(2).toString()
        val threeDaysAgo = currentDate.minusDays(3).toString()

        arr3days = listOf(oneDayAgo, twoDaysAgo, threeDaysAgo)
    }

    private fun callApiForGetLast3Days() {
        viewModel.historicalData(arr3days[counterCallApiLastDays])
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initResponseApi() {
        viewModel.historicalData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    processingOnResponseLast3DaysData(it.data!!.date, it.data.data)
                    counterCallApiLastDays +=1
                    if (counterCallApiLastDays<arr3days.size){
                        viewModel.historicalData(arr3days[counterCallApiLastDays])
                    }
                }

                is Resource.Error -> {
                    Log.d(TAG, "initResponseApi Error: ${it.message!!}")
                    showToast(it.message)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processingOnResponseLast3DaysData(date: String, data: MutableList<CurrenciesItems>) {
        val result = NumberProcessing.oneDigit(data[args.positionSecondItem].rate / data[args.positionFirstItem].rate)
        arrLast3Day.add(DataLast3Days(date, result, data[args.positionSecondItem].name))

        val dataLast3DaysSorted = sortDatesDescending(arrLast3Day)
        last3DaysAdapter.differ.submitList(dataLast3DaysSorted)

        setUpLineChart(dataLast3DaysSorted)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sortDatesDescending(dates: MutableList<DataLast3Days>): List<DataLast3Days> {
        return dates.sortedByDescending { LocalDate.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


    private fun setUpLineChart(it: List<DataLast3Days>) {
        val xAxisValues: MutableList<String> = ArrayList()

        for (element in it) {
            xAxisValues.add(element.date)
        }

        val incomeEntries = getIncomeEntries(it)
        val dataSets: java.util.ArrayList<ILineDataSet?> = ArrayList()

        val set1 = LineDataSet(incomeEntries, "Historical Exchange Rate")
        set1.color = ContextCompat.getColor(requireContext(), R.color.blue)
        set1.valueTextColor = Color.rgb(55, 70, 73)
        set1.valueTextSize = 10f
        set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        dataSets.add(set1)

        //customization
        val mLineGraph = binding.idChart
        mLineGraph.setTouchEnabled(true)
        mLineGraph.isDragEnabled = true
        mLineGraph.setScaleEnabled(true)
        mLineGraph.setPinchZoom(false)
        mLineGraph.setDrawGridBackground(false)

        val xAxis = mLineGraph.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        set1.lineWidth = 4f
        set1.circleRadius = 3f
        set1.setDrawValues(true)
        set1.circleHoleColor = ContextCompat.getColor(requireContext(), R.color.red)
        set1.setCircleColor(ContextCompat.getColor(requireContext(), R.color.red))

        //String setter in x-Axis
        mLineGraph.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(dataSets)
        mLineGraph.data = data
        mLineGraph.animateX(1000)
        mLineGraph.invalidate()
        mLineGraph.legend.isEnabled = false
        mLineGraph.description.isEnabled = false
    }

    private fun getIncomeEntries(it: List<DataLast3Days>): ArrayList<Entry>{
        val incomeEntries: ArrayList<Entry> = ArrayList()
        for (i in it.indices){
//            incomeEntries.add(Entry((i).toFloat(), it[it.size-(i+1)].close.toFloat()))
            incomeEntries.add(Entry((i).toFloat(), it[i].currency.toFloat()))
        }
        return incomeEntries
    }



}



data class DataLast3Days(val date: String, val currency: String, val name: String)