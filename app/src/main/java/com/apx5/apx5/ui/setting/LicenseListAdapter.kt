package com.apx5.apx5.ui.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtLicenseLists
import kotlinx.android.synthetic.main.item_license.view.*

/**
 * LicenseListAdapter
 */

class LicenseListAdapter internal constructor(private val license: List<AdtLicenseLists>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class LicenseHolder internal constructor(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.tv_license_name
        val content = v.tv_license_content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_license, parent, false)
        vh = LicenseHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LicenseHolder) {

            val item = license[position]
            holder.name.text = item.name
            holder.content.text = item.content
        }
    }

    override fun getItemCount(): Int {
        return license.size
    }
}
