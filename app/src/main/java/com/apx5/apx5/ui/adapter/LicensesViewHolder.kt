package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtLicenseLists
import com.apx5.apx5.ui.utilities.PrUtils
import kotlinx.android.synthetic.main.item_license.view.*

/**
 * LicensesViewHolder
 * @desc Setting, 오픈라이센스
 */
class LicensesViewHolder(
    view: View,
    val prUtils: PrUtils
): RecyclerView.ViewHolder(view) {

    fun bind(license: AdtLicenseLists) {
        itemView.apply {
            tv_license_name.text = license.name
            tv_license_content.text = license.content
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            prUtils: PrUtils
        ): LicensesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_license, parent, false)
            return LicensesViewHolder(view, prUtils)
        }
    }
}