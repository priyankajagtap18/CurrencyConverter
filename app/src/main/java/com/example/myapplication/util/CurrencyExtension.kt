package com.example.myapplication.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * CurrencyExtension - Extension function for app
 */
class CurrencyExtension {
    companion object {
        const val DATE_FORMAT_YYYY_MM_DD = Constants.DATE_FORMAT_YYYY_MM_DD
    }
}

fun getCalendarDate(dateFormat: String, pastDayCount: Int): String {
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
    if (pastDayCount != 0) {
        calendar.add(Calendar.DAY_OF_YEAR, -pastDayCount)
    }
    return format.format((calendar.time))
}

fun String.formatValue(): String {
    return String.format("%.2f", this)
}
