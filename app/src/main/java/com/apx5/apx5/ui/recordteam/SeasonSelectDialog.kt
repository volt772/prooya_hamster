package com.apx5.apx5.ui.recordteam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.apx5.apx5.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * SeasonSelectDialog
 */

class SeasonSelectDialog : BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_season_select, container, false)

        val season17 = view.findViewById<LinearLayout>(R.id.lv_2017)
        val season18 = view.findViewById<LinearLayout>(R.id.lv_2018)
        val season19 = view.findViewById<LinearLayout>(R.id.lv_2019)
        val season20 = view.findViewById<LinearLayout>(R.id.lv_2020)

        season17.setOnClickListener(this)
        season18.setOnClickListener(this)
        season19.setOnClickListener(this)
        season20.setOnClickListener(this)
        return view
    }


    override fun onClick(view: View) {
        var searchYear = 2019
        when (view.id) {
            R.id.lv_2017 -> searchYear = 2017
            R.id.lv_2018 -> searchYear = 2018
            R.id.lv_2019 -> searchYear = 2019
            R.id.lv_2020 -> searchYear = 2020
        }

        recordNavigator?.selectSeasonYear(searchYear)
        dismiss()
    }

    companion object {

        private var recordNavigator: RecordTeamNavigator? = null

        fun getInstance(navigator: RecordTeamNavigator): SeasonSelectDialog {
            recordNavigator = navigator
            return SeasonSelectDialog()
        }
    }
}