package com.example.myapplication.util

import java.text.SimpleDateFormat
import java.util.*

class CurrencyExtension {
    companion object {
        const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
    }
}

fun getCalendarDate(dateFormat: String): String {
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
    return format.format((calendar.time))
}