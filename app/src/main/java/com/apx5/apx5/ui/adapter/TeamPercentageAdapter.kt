package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtTeamPerc

class TeamPercentageAdapter internal constructor(
    private val ctx: Context
) : BaseAdapter() {

    private val teams = mutableListOf<AdtTeamPerc>()

    internal fun clearItems() {
        teams.clear()
        notifyDataSetChanged()
    }

    override fun getCount() = teams.size

    private class RecordAllHolder {
//        lateinit var playRecent: View
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val context = parent.context
        val holder: RecordAllHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.item_team_perc, parent, false)

            holder = RecordAllHolder().apply {
//                playRecent = cv.lv_play_list
            }

            cv.tag = holder
        } else {
            holder = convertView.tag as RecordAllHolder
            cv = convertView
        }

        val team = teams[position]

//        holder.awayScore.text = playItems.awayScore.toString()

        return cv
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItem(position: Int) = teams[position]

    /* 아이템 추가*/
    internal fun addItem(team: AdtTeamPerc) {
        teams.add(team)
    }
}

