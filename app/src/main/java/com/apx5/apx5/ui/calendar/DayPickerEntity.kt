package com.apx5.apx5.ui.calendar

import java.util.*

/**
 * DayPickerEntity
 */

sealed class DayPickerEntity(
    val columnCount: Int,
    val calendarType: Int,
    val selectionType: SelectionType
) {
    /* 표기타입1 : 년월*/
    data class Month(val label: String): DayPickerEntity(
        MONTH_COLUMN_COUNT,
        CalendarType.MONTH.ordinal,
        SelectionType.NONE
    )

    /* 표기타입2 : 일*/
    data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val selection: SelectionType = SelectionType.NONE,
        val weekDay: WeekDays
    ): DayPickerEntity(DAY_COLUMN_COUNT, CalendarType.DAY.ordinal, selection)

    /* 표기타입3 : 빈칸*/
    object Empty: DayPickerEntity(
        EMPTY_COLUMN_COUNT,
        CalendarType.EMPTY.ordinal,
        SelectionType.NONE
    )
}

const val TOTAL_COLUMN_COUNT = 7
const val MONTH_COLUMN_COUNT = 7
const val DAY_COLUMN_COUNT = 1
const val EMPTY_COLUMN_COUNT = 1

/**
 * 표시타입 (위에서부터 월, 주, 일, 빈공간 으로 표시)
 */
enum class CalendarType {
    MONTH,
    DAY,
    EMPTY
}

/**
 * 선택타입 (START <--- BETWEEN ---> END) or NONE
 */
enum class SelectionType {
    SELECTED,
    NONE
}

/**
 * 요일검사
 */
enum class WeekDays(val dayNum: Int) {
    SUN(1), MON(2), TUE(3), WED(4), THU(5), FRI(6), SAT(7), ETC(0)
}