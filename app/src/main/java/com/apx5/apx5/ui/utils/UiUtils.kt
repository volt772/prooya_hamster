package com.apx5.apx5.ui.utils

import android.content.Context
import android.text.TextUtils
import com.apx5.apx5.ext.equalsExt
import com.apx5.apx5.ext.splitExt
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.util.*

/**
 * UiUtils
 */

class UiUtils {
    companion object {
        /**
         * 날자 변환
         * @param dateString
         * @return
         */
        fun getDateToFull(dateString: String): String {
            if (TextUtils.isEmpty(dateString)) {
                return ""
            }

            val year = dateString.substring(2, 4)
            val month = dateString.substring(4, 6)
            val day = dateString.substring(6, 8)

            return String.format(Locale.getDefault(), "%s. %s. %s", year, month, day)
        }

        fun getTime(time: String): String {
            if (time.isBlank()) return ""

            /* 경기시작시간 없는경우*/
            return if (time.equalsExt("0")) {
                ""
            } else {
                val hour = time.substring(0, 2)
                val min = time.substring(2, 4)
                String.format(Locale.getDefault(), "%s:%s", hour, min)
            }
        }

        fun getDateToReadableMonthDay(dateString: String): String {
            if (TextUtils.isEmpty(dateString)) {
                return ""
            }

            val month = dateString.substring(4, 6)
            val day = dateString.substring(6, 8)

            return String.format(Locale.getDefault(), "%s월 %s일", month, day)
        }

        /**
         * 연도
         */
        fun getYear(dateString: String): String {
            return if (dateString.isBlank()) "" else dateString.substring(0, 4)
        }

        /**
         * 날자변환(yyyy.mm.dd)
         */
        fun getDateToAbbr(dateString: String, divider: String): String {
            if (dateString.isBlank()) return ""

            val year = dateString.substring(2, 4)
            val month = dateString.substring(4, 6)
            val day = dateString.substring(6, 8)

            return String.format(Locale.getDefault(), "%s%s%s%s%s", year, divider, month, divider, day)
        }

        /**
         * 현재연도 (y)
         */
        val currentYear: Int
            get() {
                val date = DateTime()

                return if (date.year > 2020) { 2020 } else { date.year }
            }

        /**
         * 현재일자(yyyymmdd)
         */
        val today: String
            get() {
                val localDate = LocalDate()
                return localDate.toString().replace("-", "")
            }

        fun getTodaySeparate(type: String): Int {
            val localDate = LocalDate()
            val today = localDate.toString()
            val todayArr = today.splitExt("-")

            when (type) {
                "year" -> return Integer.parseInt(todayArr[0])
                "month" -> return Integer.parseInt(todayArr[1])
                "day" -> return Integer.parseInt(todayArr[2])
            }

            return 0
        }

        /**
         * Drawable Int
         */
        fun getDrawableByName(context: Context, name: String): Int {
            return context.resources.getIdentifier(name, "drawable", context.packageName)
        }
    }
}