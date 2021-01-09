package com.apx5.apx5.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamPerc
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_game_all.view.*
import kotlinx.android.synthetic.main.item_team_perc.view.*

class TeamPercentageAdapter(
    val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val teams = mutableListOf<AdtTeamPerc>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_team_perc, parent, false)
        return TeamPercViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamPercViewHolder).bind(teams[position])
    }

    override fun getItemCount() = teams.size

    inner class TeamPercViewHolder(
            private val context: Context,
            view: View
    ): RecyclerView.ViewHolder(view) {

        fun bind(team: AdtTeamPerc) {
            /* 팀 스코어*/
            itemView.iv_team_emblem.setImageResource(UiUtils.getDrawableByName(context, team.team.emblem))
            itemView.pv_percentage.apply {
                progress = team.winningRate.toFloat()
            }
            itemView.tv_percentage.text = "${team.winningRate}%"

//            itemView.tv_away_score.text = game.awayScore.toString()
        }
    }

    /* 아이템 추가*/
    internal fun addItem(team: AdtTeamPerc) {
        teams.add(team)
    }

    /* 아이템 전체초기화*/
    internal fun clearItems() {
        teams.clear()
        notifyDataSetChanged()
    }
}
