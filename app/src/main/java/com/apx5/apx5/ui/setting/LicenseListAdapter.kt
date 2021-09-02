package com.apx5.apx5.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.databinding.ItemLicenseBinding
import com.apx5.apx5.datum.adapter.AdtLicenseLists

/**
 * LicenseListAdapter
 */


class LicenseListAdapter internal constructor(
    private val license: List<AdtLicenseLists>
): RecyclerView.Adapter<LicenseListAdapter.LicenseListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LicenseListViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_license,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: LicenseListViewHolder,
        position: Int
    ) {
        holder.bind(license, position)
    }

    override fun getItemCount() = license.size

    inner class LicenseListViewHolder(
        val binding: ItemLicenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(licenses: List<AdtLicenseLists>, position: Int) {
            val lic = licenses[position]

            binding.tvLicenseName.text = lic.name

            binding.tvLicenseContent.text = lic.content

//            binding.tvTeamName.text = team.teamName
//            displayImageRound(context, binding.ivTeamEmblem, team.teamImage)
//
//            binding.clTeamRoot.setOnClickListener(object : PrSingleClickListener() {
//                override fun onSingleClick(view: View) {
//                    selectFunc.invoke(team)
//                }
//            })
        }
    }
}