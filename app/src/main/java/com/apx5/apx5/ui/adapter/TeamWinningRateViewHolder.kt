package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.ui.utilities.PrUtils
import kotlinx.android.synthetic.main.item_team_winning_rate.view.*

/**
 * TeamWinningRateViewHolder
 * @desc Statics, 팀별통산승률
 */
class TeamWinningRateViewHolder(
    view: View,
    val prUtils: PrUtils
): RecyclerView.ViewHolder(view) {

    fun bind(team: AdtTeamWinningRate) {
        itemView.apply {
            iv_team_emblem.setImageResource(prUtils.getDrawableByName(context, team.team.emblem))

            val percentage = if (team.winningRate == 0) 1f else team.winningRate.toFloat()

            pv_percentage.progress = percentage
            tv_percentage.text = String.format("%d%%", team.winningRate)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            prUtils: PrUtils
        ): TeamWinningRateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_team_winning_rate, parent, false)
            return TeamWinningRateViewHolder(view, prUtils)
        }
    }
}