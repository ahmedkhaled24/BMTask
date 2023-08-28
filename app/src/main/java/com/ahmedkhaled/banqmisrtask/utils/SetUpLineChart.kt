package com.ahmedkhaled.banqmisrtask.utils

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.DataLast3Days
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

object SetUpLineChart {
    fun setChart(context: Context, idChart: LineChart, it: List<DataLast3Days>){
        val xAxisValues: MutableList<String> = ArrayList()

        for (element in it) {
            xAxisValues.add(element.date)
        }

        val incomeEntries = getIncomeEntries(it)
        val dataSets: java.util.ArrayList<ILineDataSet?> = ArrayList()

        val set1 = LineDataSet(incomeEntries, "Historical Exchange Rate")
        set1.color = ContextCompat.getColor(context, R.color.blue)
        set1.valueTextColor = Color.rgb(55, 70, 73)
        set1.valueTextSize = 10f
        set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        dataSets.add(set1)

        //customization
        val mLineGraph = idChart
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
        set1.circleHoleColor = ContextCompat.getColor(context, R.color.red)
        set1.setCircleColor(ContextCompat.getColor(context, R.color.red))

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
        for (i in it.indices)
            incomeEntries.add(Entry((i).toFloat(), it[i].currency.toFloat()))

        return incomeEntries
    }
}