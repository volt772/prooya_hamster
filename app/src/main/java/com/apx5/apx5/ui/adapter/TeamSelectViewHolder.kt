package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.apx5.apx5.ui.utilities.PrUtils
import kotlinx.android.synthetic.main.item_season_record.view.*

/**
 * TeamSelectViewHolder
 * @desc Dashboard / Setting, 팀 선택
 */
class TeamSelectViewHolder(
    view: View,
    val prUtils: PrUtils,
    private val selectTeam: ((AdtTeamSelection) -> Unit)?= null
): RecyclerView.ViewHolder(view) {

    fun bind(team: AdtTeamSelection) {
        itemView.apply {
            tv_team_name.text = team.teamName

            val teamResource = String.format("ic_team_%s", team.teamCode)
            iv_team_emblem.setImageResource(
                prUtils.getDrawableByName(context, teamResource)
            )

            setOnClickListener(object : PrSingleClickListener() {
                override fun onSingleClick(view: View) {
                    selectTeam?.invoke(team)
                }
            })
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            prUtils: PrUtils,
            selectTeam: ((AdtTeamSelection) -> Unit)?= null
        ): TeamSelectViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_team, parent, false)
            return TeamSelectViewHolder(view, prUtils, selectTeam)
        }
    }
}