package com.apx5.apx5.ui.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.ui.utils.MaterialTools
import com.apx5.apx5.ui.utils.OnSingleClickListener
import kotlinx.android.synthetic.main.item_team.view.*

/**
 * TeamListAdapter
 */

class TeamListAdapter internal constructor(
    private val ctx: Context,
    private val selectFunc: (AdtTeamSelection) -> Unit
) : BaseAdapter() {

    private val teamList = mutableListOf<AdtTeamSelection>()

    override fun getCount() = teamList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItem(position: Int) = teamList[position]

    private class TeamHolder {
        lateinit var lytParent: View
        lateinit var ivTeamEmblem: ImageView
        lateinit var tvTeamName: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val context = parent.context
        val holder: TeamHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.item_team, parent, false)

            holder = TeamHolder().apply {
                lytParent = cv.lyt_parent
                ivTeamEmblem = cv.iv_team_emblem
                tvTeamName = cv.tv_team_name
            }

            cv.tag = holder
        } else {
            holder = convertView.tag as TeamHolder
            cv = convertView
        }

        val teamItems = teamList[position]
        holder.tvTeamName.text = teamItems.teamName
        MaterialTools.displayImageRound(ctx, holder.ivTeamEmblem, teamItems.teamImage)

        holder.lytParent.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) { selectFunc.invoke(teamItems) }
        })

        return cv
    }

    /* 아이템 추가*/
    internal fun addItem(team: AdtTeamSelection) {
        teamList.add(team)
    }
}