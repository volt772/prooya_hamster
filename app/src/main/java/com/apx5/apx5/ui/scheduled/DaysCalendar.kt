package com.apx5.apx5.ui.scheduled

import android.app.Activity
import android.app.DatePickerDialog
import com.apx5.apx5.R
import java.util.*

/**
 * DaysCalendar
 */

class DaysCalendar {
    companion object {
        var todayYear = 0
        var todayMonth = 0
        var todayDay = 0

        /**
         * datePickerListener
         * @param searchPlay DaysFragment::searchPlayByDate()
         * @return DatePickerDialog.OnDateSetListener
         */
        fun datePickerListener(
            searchPlay : (String) -> Unit
        ) : DatePickerDialog.OnDateSetListener {
            return DatePickerDialog.OnDateSetListener {
                _, year, monthOfYear, dayOfMonth ->
                var month = monthOfYear
                month += 1
                todayYear = year
                todayMonth = month
                todayDay = dayOfMonth

                val searchDate = String.format(
                    Locale.getDefault(),
                    "%d%02d%02d", todayYear, todayMonth, todayDay
                )

                searchPlay(searchDate)
            }
        }

        /**
         * datePickerDialog
         * @param activity DaysFragment::requireActivity
         * @param listener DatePickerDialog.OnDateSetListener
         * @return datePickerDialog
         */
        fun datePickerDialog(
            activity: Activity,
            listener: DatePickerDialog.OnDateSetListener
        ) : DatePickerDialog {
            return DatePickerDialog(
                activity,
                R.style.ProoyaDatePickerTheme,
                listener,
                todayYear,
                todayMonth - 1,
                todayDay
            )
        }
    }
}