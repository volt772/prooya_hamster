package com.apx5.apx5.ui.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.databinding.ItemTeamBinding
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.ext.displayImageRound
import com.apx5.apx5.ui.listener.PrSingleClickListener

/**
 * TeamListAdapter
 */

class TeamListAdapter internal constructor(
    private val context: Context,
    private val teams: List<AdtTeamSelection>,
    private val selectFunc: (AdtTeamSelection) -> Unit
): RecyclerView.Adapter<TeamListAdapter.TeamListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TeamListViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_team,
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: TeamListViewHolder,
        position: Int
    ) {
        holder.bind(teams, position)
    }

    override fun getItemCount() = teams.size

    inner class TeamListViewHolder(
        val binding: ItemTeamBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(teamList: List<AdtTeamSelection>, position: Int) {
            val team = teamList[position]

            binding.tvTeamName.text = team.teamName
            displayImageRound(context, binding.ivTeamEmblem, team.teamImage)

            binding.clTeamRoot.setOnClickListener(object : PrSingleClickListener() {
                override fun onSingleClick(view: View) {
                    selectFunc.invoke(team)
                }
            })
        }
    }
}