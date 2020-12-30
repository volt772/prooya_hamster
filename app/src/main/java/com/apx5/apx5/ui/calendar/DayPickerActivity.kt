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
import kotlinx.android.synthetic.main.activity_day_picker.*
import org.joda.time.DateTime
import java.util.*
import java.util.Calendar.getInstance


/**
 * DayPickerActivity
 */
class DayPickerActivity : Activity() {
    private var queryStartDate: DateTime?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_day_picker)

        /* 상태바 색상*/
        setSystemBarColor(this, R.color.p_main_first)

//        /* 기본일자 설정*/
        val defaultCalDate = getInstance()
        defaultCalDate.set(2020, 3, 1, 0, 0, 0)

        /* Picker 초기설정*/
        rp_search_date.apply {
            setSelectionDefaultDate(::setDefaultDate)
            setSelectionDate(defaultCalDate.time)
        }

        /* Picker 단일 선택*/
        rp_search_date.setDaySelectedListener { startDate, label ->
            atv_start_date.text = label
            atv_end_date.text = "-"
            setDateTimeForQuery(DaySelectType.SINGLE, startDate)
        }

        /* '닫기'*/
        iv_picker_close.setOnClickListener {
            finish()
        }

        /* '기간선택'*/
        iv_picker_confirm.setOnClickListener {
            /**
             * '종료일' 없을경우, '시작일'로 대체 (당일검색으로 판단)
             * '시작일', '종료일'은 null 이 될 수 없음
             */
//            val queryEndDate = checkEndDate()
//
//            if (queryEndDate != null) {
//                val rData = Intent()
//                rData.putExtra(MpIntent.Eas.SELECTED_START_DAY, queryStartDate)
//                rData.putExtra(MpIntent.Eas.SELECTED_END_DAY, queryEndDate)
//                setResult(RESULT_OK, rData)
//                finish()
//            } else {
//                Toast.makeText(this, resources.getString(R.string.range_picker_invalid_range), Toast.LENGTH_SHORT).show()
//            }
        }
    }

    /**
     * 캘린더 생성 후, 기본 일자 선택 (오늘)
     */
    private fun setDefaultDate(defaultDate: DayPickerEntity.Day) {
        atv_start_date.text = defaultDate.prettyLabel
        atv_end_date.text = defaultDate.prettyLabel
        setDateTimeForQuery(DaySelectType.DEFAULT, defaultDate.date)
    }

    /**
     * 쿼리용 데이터 저장
     * @desc Date형식 > DateFormat형식
     */
    private fun setDateTimeForQuery(selectType: DaySelectType, startDate: Date, endDate: Date?= null) {
        queryStartDate = DateTime(startDate)
    }

    fun setSystemBarColor(act: Activity, @ColorRes color: Int) {
        val window = act.window
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = ContextCompat.getColor(act, color)
        }
    }

    enum class DaySelectType { DEFAULT, SINGLE }

    companion object {
        fun newIntent(context: Context) = Intent(context, DayPickerActivity::class.java)
    }
}