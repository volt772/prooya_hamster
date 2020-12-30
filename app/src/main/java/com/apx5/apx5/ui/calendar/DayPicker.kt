package com.apx5.apx5.ui.calendar

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.ui.calendar.ext.isTheSameDay
import com.apx5.apx5.ui.calendar.ext.withTime
import java.util.*
import java.util.Calendar.*

/**
 * DayPicker
 */

class DayPicker : RecyclerView {
    private val timeZone = TimeZone.getDefault()
    private val locale = Locale.getDefault()

    /* Picker Adapter*/
    private val dayPickerAdapter = DayPickerAdapter()

    /* '월' 표기 다국어*/
    private var labelForMonth: String = ""

    /* '전체' 표기 다국어*/
    private var labelForFullDate: String = ""

    /* 시작 캘린더(월)*/
    private val startDay = getInstance(timeZone, locale)

    /* 종료 캘린더(월)*/
    private val endDay = getInstance(timeZone, locale)

    /* 초기화 캘린더(월)*/
    private val initDay = getInstance(timeZone, locale)

    /* 캘린더 데이터 리스트*/
    private var calendarData: MutableList<DayPickerEntity> = mutableListOf()

    /* 선택 : 시작일자*/
    private var selectedDate: SelectedDate? = null

    /* 선택 : 캘린더 생성 후, 기본 일자 선택 (오늘)*/
    private var defaultDateFunc:(DayPickerEntity.Day) -> Unit = { _ -> }

    /* 선택[UI표시] : 단일선택*/
    private var onDaySelectedListener: (startDate: Date, label: String) -> Unit = { _, _ -> }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    )

    init {
        /* '월' 다국어*/
        labelForMonth = context.resources.getString(R.string.day_picker_month_title)
        /* '전체형식' 다국어*/
        labelForFullDate = context.resources.getString(R.string.day_picker_date_label)

        startDay.set(2017, 2, 1, 0, 0, 0)
        endDay.set(2020, 10, 30, 0, 0, 0)
        initDay.set(2020, 3, 1, 0, 0, 0)

        setBackgroundColor(ContextCompat.getColor(context, R.color.p_white_10))
        initAdapter()
        initListener()
    }

    /**
     * 캘린더 생성 후, 기본 일자 선택 (오늘)
     */
    fun setSelectionDefaultDate(callback:(DayPickerEntity.Day) -> Unit) {
        defaultDateFunc = callback
    }

    /**
     * UI에서 범위 지정
     */
    fun setSelectionDate(date: Date) {
        selectDate(date)
    }

    /**
     * 일자 선택
     */
    private fun selectDate(date: Date) {
        val index = calendarData.indexOfFirst { it is DayPickerEntity.Day && it.date.isTheSameDay(date) }
        onDaySelected(calendarData[index] as DayPickerEntity.Day, index)
    }

    /**
     * 캘린더 해당 월로 스크롤
     */
    private fun scrollToDate(date: Date) {
        val index = calendarData.indexOfFirst { it is DayPickerEntity.Day && it.date.isTheSameDay(date) }
        if (index > -1) {
            scrollToPosition(index)
        }
    }

    /**
     * 단일 Callback Listener
     */
    fun setDaySelectedListener(callback: (startDate: Date, label: String) -> Unit) {
        onDaySelectedListener = callback
    }

    /**
     * 캘린더 Adapter 초기설정
     */
    private fun initListener() {
        dayPickerAdapter.onActionListener = { item, position ->
            if (item is DayPickerEntity.Day) onDaySelected(item, position)
        }
    }

    /**
     * 캘린더 Adapter 생성
     */
    private fun initAdapter() {
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return calendarData[position].columnCount
                }
            }
        }

        adapter = dayPickerAdapter
        refreshData()

        /* 생성 후, 기본 '월' 위치로 이동*/
        scrollToDate(initDay.time)
    }

    /**
     * 캘린더 Data Refresh
     */
    private fun refreshData() {
        calendarData = buildCalendarData()
        dayPickerAdapter.setData(calendarData)
    }

    /**
     * 캘린더 Data 생성
     */
    private fun buildCalendarData(): MutableList<DayPickerEntity> {
        val calendarData = mutableListOf<DayPickerEntity>()
        val cal = getInstance(timeZone, locale)
        cal.withTime(startDay.time)

        /**
         * @desc 시작 캘린더 Year 부터 종료 캘린더 Year 까지 생성
         * @desc View Type
         * 1. 월 : 년 및 월을 보여주는 타이틀
         * 2. 요일 : 요일을 보여주는 레이블
         * 3. 일 : 각각 일을 표시
         * 4. 빈칸 : 월의 시작 및 마지막에 비어있는 뷰
         */
        val monthDifference = endDay.totalMonthDifference(startDay)

        cal.set(DAY_OF_MONTH, 1)
        (0..monthDifference).forEach { _ ->
            val totalDayInAMonth = cal.getActualMaximum(DAY_OF_MONTH)
            (1..totalDayInAMonth).forEach { _ ->
                val day = cal.get(DAY_OF_MONTH)
                val dayOfWeek = cal.get(DAY_OF_WEEK)

                when (day) {
                    /* 월 시작일 (empty 먼저추가)*/
                    1 -> {
                        calendarData.add(DayPickerEntity.Month(cal.toPrettyMonthOnly(labelForMonth)))
                        calendarData.addAll(createStartEmptyView(dayOfWeek))
                        calendarData.add(
                            DayPickerEntity.Day(
                                day.toString(),
                                cal.toPrettyDateString(labelForFullDate),
                                cal.time,
                                weekDay = getWeekByDay(dayOfWeek)
                            )
                        )
                    }
                    /* 월 종료일 (empty 나중 추가)*/
                    totalDayInAMonth -> {
                        calendarData.add(
                            DayPickerEntity.Day(
                                day.toString(),
                                cal.toPrettyDateString(labelForFullDate),
                                cal.time,
                                weekDay = getWeekByDay(dayOfWeek)
                            )
                        )
                        calendarData.addAll(createEndEmptyView(dayOfWeek))
                    }
                    /* 그 외*/
                    else -> {
                        calendarData.add(
                            DayPickerEntity.Day(
                                day.toString(),
                                cal.toPrettyDateString(labelForFullDate),
                                cal.time,
                                weekDay = getWeekByDay(dayOfWeek)
                            )
                        )
                    }
                }
                cal.add(DATE, 1)
            }
        }

        return calendarData
    }

    /**
     * 시작 Empty View (요일따라 앞에 Empty View 추가)
     */
    private fun createStartEmptyView(dayOfWeek: Int): List<DayPickerEntity.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            MONDAY -> 1
            TUESDAY -> 2
            WEDNESDAY -> 3
            THURSDAY -> 4
            FRIDAY -> 5
            SATURDAY -> 6
            else -> 0
        }

        val listEmpty = mutableListOf<DayPickerEntity.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(DayPickerEntity.Empty) }
        return listEmpty
    }

    /**
     * 종료 Empty View (요일따라 뒤에 Empty View 추가)
     */
    private fun createEndEmptyView(dayOfWeek: Int): List<DayPickerEntity.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            SUNDAY -> 6
            MONDAY -> 5
            TUESDAY -> 4
            WEDNESDAY -> 3
            THURSDAY -> 2
            FRIDAY -> 1
            else -> 6
        }

        val listEmpty = mutableListOf<DayPickerEntity.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(DayPickerEntity.Empty) }
        return listEmpty
    }

    /**
     * 일자 선택
     */
    private fun onDaySelected(item: DayPickerEntity.Day, position: Int) {
        if (item == selectedDate?.day) return

        resetSelection()
        assignAsStartDate(item, position)

        dayPickerAdapter.setData(calendarData)
    }

    /**
     * 선택 초기화
     */
    private fun resetSelection() {
        val startDatePosition = selectedDate?.position

        if (startDatePosition != null) {
            val entity = calendarData[startDatePosition]
            if (entity is DayPickerEntity.Day) {
                calendarData[startDatePosition] = entity.copy(selection = SelectionType.NONE)
            }
        }
    }

    /**
     * 시작일자 할당
     */
    private fun assignAsStartDate(
        item: DayPickerEntity.Day,
        position: Int
    ) {
        val newItem = item.copy(selection = SelectionType.SELECTED)
        calendarData[position] = newItem
        selectedDate = SelectedDate(newItem, position)
        onDaySelectedListener.invoke(item.date, item.prettyLabel)
    }

    /**
     * 요일계산
     * @desc Round 음영처리하기 위함
     */
    private fun getWeekByDay(weekDay: Int): WeekDays {
        return when (weekDay) {
            1 -> WeekDays.SUN
            2 -> WeekDays.MON
            3 -> WeekDays.TUE
            4 -> WeekDays.WED
            5 -> WeekDays.THU
            6 -> WeekDays.FRI
            7 -> WeekDays.SAT
            else -> WeekDays.ETC
        }
    }

    internal data class SelectedDate(val day: DayPickerEntity.Day, val position: Int)
}