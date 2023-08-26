package com.ahmedkhaled.banqmisrtask.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ConvertDateFormat {
    @RequiresApi(Build.VERSION_CODES.O)
    fun date(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(formatter)
    }

}