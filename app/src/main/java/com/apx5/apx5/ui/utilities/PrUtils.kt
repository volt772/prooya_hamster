package com.apx5.apx5.ui.utilities

import android.content.Context

/**
 * PrUtils
 */
interface PrUtils {

    val currentYear: Int

    val today: String

    fun getDateToFull(dateString: String): String

    fun getTime(time: String): String

    fun getDateToReadableMonthDay(dateString: String): String

    fun getYear(dateString: String): String

    fun getDateToAbbr(dateString: String, divider: String): String

    fun getTodaySeparate(type: String): Int

    fun getDrawableByName(context: Context, name: String): Int
}