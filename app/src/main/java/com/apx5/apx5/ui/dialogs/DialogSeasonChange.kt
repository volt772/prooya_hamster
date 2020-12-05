package com.apx5.apx5.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.apx5.apx5.R
import com.apx5.apx5.ui.utils.UiUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * DialogSeasonChange
 */

class DialogSeasonChange(
    val callback: (Int) -> Unit
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

        val currentYear = UiUtils.currentYear

        npYear.minValue = MIN_YEAR
        npYear.maxValue = MAX_YEAR
        npYear.wrapSelectorWheel = true

        npYear.apply {
            minValue = MIN_YEAR
            maxValue = currentYear
            wrapSelectorWheel = true
            value = currentYear
        }

        btnSelect.setOnClickListener {
            val selectedYear = npYear.value
            callback(selectedYear)
            dismiss()
        }

        return view
    }

    companion object {
        const val MIN_YEAR = 2017
        const val MAX_YEAR = 2021
    }
}