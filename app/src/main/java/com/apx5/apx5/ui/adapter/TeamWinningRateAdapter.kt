package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_team_winning_rate.view.*

/**
 * TeamWinningRateAdapter
 */
class TeamWinningRateAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val teams = mutableListOf<AdtTeamWinningRate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_team_winning_rate, parent, false)
        return TeamPercViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamPercViewHolder).bind(teams[position])
    }

    override fun getItemCount() = teams.size

    inner class TeamPercViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(team: AdtTeamWinningRate) {
            itemView.apply {
                iv_team_emblem.setImageResource(UiUtils.getDrawableByName(context, team.team.emblem))

                val percentage = if (team.winningRate == 0) { 1f } else { team.winningRate.toFloat() }

                pv_percentage.progress = percentage
                tv_percentage.text = "${team.winningRate}%"
            }
        }
    }

    /* 아이템 추가*/
    internal fun addItem(team: AdtTeamWinningRate) {
        teams.add(team)
    }

    /* 아이템 전체초기화*/
    internal fun clearItems() {
        teams.clear()
        notifyDataSetChanged()
    }
}
