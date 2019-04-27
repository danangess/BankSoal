package com.example.banksoal.utils

import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

    val timestamp: String
        get() = SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())
}