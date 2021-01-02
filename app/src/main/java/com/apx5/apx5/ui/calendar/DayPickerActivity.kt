package com.apx5.apx5.ui.calendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.datum.DtQueryDateTime
import com.apx5.apx5.ui.utils.UiUtils.Companion.getDateToFullForQuery
import kotlinx.android.synthetic.main.activity_day_picker.*
import org.joda.time.DateTime
import java.util.*
import java.util.Calendar.getInstance


/**
 * DayPickerActivity
 */
class DayPickerActivity : Activity() {
    private var querySelectedDate: DateTime?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* 기본일자 설정*/
        val defaultDateTime = makeQueryDate(
            with(intent) {
                getStringExtra(PrConstants.Intent.DAY_PICKED)
            }
        )

        val defaultCalDate = getInstance()
        defaultCalDate.set(
            defaultDateTime.year,
            defaultDateTime.month - 1,
            defaultDateTime.day,
            defaultDateTime.hour,
            defaultDateTime.min,
            defaultDateTime.sec
        )

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_day_picker)

        /* 상태바 색상*/
        setSystemBarColor(this, R.color.p_main_first)

        /* Picker 초기설정*/
        rp_search_date.apply {
            setSelectionDefaultDate(::setDefaultDate)
            setSelectionDate(defaultCalDate.time, defaultDateTime)
        }

        /* Picker 단일 선택*/
        rp_search_date.setDaySelectedListener { selectedDate, label ->
            atv_selected_date.text = label
            setDateTimeForQuery(DaySelectType.SINGLE, selectedDate)
        }

        /* '닫기'*/
        iv_picker_close.setOnClickListener {
            finish()
        }

        /* '기간선택'*/

        iv_picker_confirm.setOnClickListener {
            val dateString = if (querySelectedDate != null) {
                querySelectedDate?.let { _date ->
                    val year = _date.year
                    val month = String.format(MONTH_DAY_FORMAT, _date.monthOfYear)
                    val day = String.format(MONTH_DAY_FORMAT, _date.dayOfMonth)

                    "${year}${month}${day}"
                }
            } else {
                DEFAULT_DATE
            }

            val rData = Intent()
            rData.putExtra(PrConstants.Intent.DAY_PICKED, dateString)
            setResult(RESULT_OK, rData)
            finish()
        }
    }

    private fun makeQueryDate(_date: String) = if (_date.isBlank()) {
            DtQueryDateTime()
        } else {
            getDateToFullForQuery(_date)
        }

    /**
     * 캘린더 생성 후, 기본 일자 선택 (오늘)
     */
    private fun setDefaultDate(defaultDate: DayPickerEntity.Day) {
        atv_selected_date.text = defaultDate.prettyLabel
//        atv_end_date.text = defaultDate.prettyLabel
        setDateTimeForQuery(DaySelectType.DEFAULT, defaultDate.date)
    }

    /**
     * 쿼리용 데이터 저장
     * @desc Date형식 > DateFormat형식
     */
    private fun setDateTimeForQuery(selectType: DaySelectType, selectedDate: Date) {
        querySelectedDate = DateTime(selectedDate)
    }

    private fun setSystemBarColor(act: Activity, @ColorRes color: Int) {
        val window = act.window
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = ContextCompat.getColor(act, color)
        }
    }

    enum class DaySelectType { DEFAULT, SINGLE }

    companion object {
        const val DEFAULT_DATE = "20200401"
        const val MONTH_DAY_FORMAT = "%02d"

        fun newIntent(context: Context) = Intent(context, DayPickerActivity::class.java)

    }
}