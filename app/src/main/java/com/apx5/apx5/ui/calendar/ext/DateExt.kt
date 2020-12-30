package com.apx5.apx5.ui.calendar.ext

import org.joda.time.DateTime
import java.util.*

/**
 * 같은 날인지 검사
 */
internal fun Date.isTheSameDay(comparedDate: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.withTime(this)
    val comparedCalendarDate = Calendar.getInstance()
    comparedCalendarDate.withTime(comparedDate)
    return calendar.get(Calendar.DAY_OF_YEAR) == comparedCalendarDate.get(Calendar.DAY_OF_YEAR) && calendar.get(
        Calendar.YEAR
    ) == comparedCalendarDate.get(Calendar.YEAR)
}

/**
 * 날자 기준으로만 검사 : 시간은 모두 '0'
 */
internal fun Calendar.withTime(date: Date) {
    clear()
    time = date
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

/**
 * 시간 Formatted
 * @param convType
 * 1 : YY.MM.dd HH:mm
 * 2 : HH:mm
 * 3 : YY-MM-dd HH:mm
 * 4 : YYYY-MM-dd
 * 5 : YY-MM-dd
 * etc : YY-MM-dd HH:mm
 */
fun DateTime?.convertDateByType(type: Int): String {
    if (this == null) {
        return ""
    }

    return when (type) {
        1 -> this.toString("YY.MM.dd HH:mm")
        2 -> this.toString("HH:mm")
        3 -> this.toString("YY-MM-dd HH:mm")
        4 -> this.toString("YYYY-MM-dd")
        5 -> this.toString("YY-MM-dd")
        else -> this.toString("YY.MM.dd HH:mm")
    }
}

class DateExt {
    companion object {
        /**
         * 현재 일자 (분리형)
         * @return DateOfYear
         */
        fun getDateOfToday(): DateOfToday {
            val date = DateTime()
            val converted = date.convertDateByType(4)

            if (converted.equalsExt("")) {
                return DateOfToday(0, 0, 0)
            }

            val formatted = converted.splitExt("-")

            return if (formatted.isNullOrEmpty()) {
                return DateOfToday(0, 0, 0)
            } else {
                DateOfToday(
                    year = formatted[0].toInt(),
                    month = formatted[1].toInt(),
                    day = formatted[2].toInt()
                )
            }
        }
    }

    data class DateOfToday(val year: Int, val month: Int, val day: Int)
}
