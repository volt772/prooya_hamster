package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.adapter.AdtLicenseLists
import com.apx5.apx5.datum.adapter.AdtTeamLists
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.apx5.apx5.ui.utilities.PrUtils
import kotlinx.android.synthetic.main.item_license.view.*
import kotlinx.android.synthetic.main.item_team_record.view.*
import kotlinx.android.synthetic.main.item_team_winning_rate.view.iv_team_emblem

/**
 * LicenseViewHolder
 * @desc Setting, 오픈라이센스
 */
class LicenseViewHolder(
    view: View,
    val prUtils: PrUtils
): RecyclerView.ViewHolder(view) {

    fun bind(license: AdtLicenseLists) {
        itemView.apply {
            println("probe : name : ${license.name}")
            /* 라이센스 이름*/
            tv_license_name.text = license.name

            /* 라이센스 내용*/
            tv_license_content.text = license.content
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            prUtils: PrUtils
        ): LicenseViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_license, parent, false)
            return LicenseViewHolder(view, prUtils)
        }
    }
}