package com.ahmedkhaled.banqmisrtask.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object NumberProcessing {
    fun oneDigit(num: Double): String {
        val nf = NumberFormat.getCurrencyInstance(Locale.ENGLISH)
        val dd = nf as DecimalFormat
        dd.applyPattern("#.###")
        return dd.format(num)
    }
}