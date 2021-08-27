package com.apx5.apx5.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrDialogYearSelectType
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * DialogSeasonChange
 */

class DialogSeasonChange(
    val callback: (Int) -> Unit,
    private val selectedYear: Int,
    private val selectType: PrDialogYearSelectType
): BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.YearSelectionRoundStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_season_change, container, false)

        val npYear = view.findViewById<NumberPicker>(R.id.np_year)
        val btnSelect = view.findViewById<Button>(R.id.btn_select)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)

        /* 연도*/
        npYear.apply {
            setDividerHeight(2)

            minValue = MIN_YEAR
            maxValue = MAX_YEAR
            wrapSelectorWheel = true
            value = selectedYear
        }

        /* 타이틀*/
        tvTitle.text = selectType.displayName

        /* 시즌선택*/
        btnSelect.setOnClickListener(object : PrSingleClickListener() {
            override fun onSingleClick(view: View) {
                callback(npYear.value)
                dismiss()
            }
        })

        return view
    }

    private fun NumberPicker.setDividerHeight(height: Int) {
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDividerHeight") {
                pf.isAccessible = true
                try {
                    pf.set(this, height)
                } catch (e: IllegalAccessException) {
                    /**/
                }
                break
            }
        }
    }

    companion object {
        const val MIN_YEAR = 2017
        const val MAX_YEAR = 2020
    }
}