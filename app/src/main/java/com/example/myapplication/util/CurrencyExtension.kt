package com.example.myapplication.util

import java.text.SimpleDateFormat
import java.util.*

class CurrencyExtension {
    companion object {
        const val DATE_FORMAT_YYYY_MM_DD = Constants.DATE_FORMAT_YYYY_MM_DD
    }
}

fun getCalendarDate(dateFormat: String): String {
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
    return format.format((calendar.time))
}