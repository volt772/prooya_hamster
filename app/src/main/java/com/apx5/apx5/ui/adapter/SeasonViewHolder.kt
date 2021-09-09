package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.adapter.AdtTeamLists
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.apx5.apx5.ui.utilities.PrUtils
import kotlinx.android.synthetic.main.item_season_record.view.*
import kotlinx.android.synthetic.main.item_team_winning_rate.view.iv_team_emblem

/**
 * SeasonViewHolder
 * @desc Season, 시즌기록
 */
class SeasonViewHolder(
    view: View,
    val prUtils: PrUtils,
    private val selectGame: ((Int, String) -> Unit)?= null
): RecyclerView.ViewHolder(view) {

    fun bind(team: AdtTeamLists) {
        itemView.apply {
            /* 팀 엠블럼*/
            iv_team_emblem.setImageResource(team.teamEmblem)

            /* 팀 이름*/
            tv_team_name.text = PrTeam.team(team.team).fullName

            /* 승*/
            tv_win.text = team.win.toString()

            /* 무*/
            tv_draw.text = team.draw.toString()

            /* 패*/
            tv_lose.text = team.lose.toString()

            /* 승률*/
            tv_rate.text = team.rate.toString()

            setOnClickListener(object : PrSingleClickListener() {
                override fun onSingleClick(view: View) { selectGame?.invoke(team.year, team.team) }
            })
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            prUtils: PrUtils,
            selectGame: ((Int, String) -> Unit)?= null
        ): SeasonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_season_record, parent, false)
            return SeasonViewHolder(view, prUtils, selectGame)
        }
    }
}