package com.apx5.apx5.ui.recordteam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.adapter.AdtTeamLists
import kotlinx.android.synthetic.main.item_team_record.view.*

/**
 * RecordTeamAdapter
 */

class RecordTeamAdapter internal constructor(
    private val nav: RecordTeamNavigator
) : BaseAdapter() {

    private val teamList = mutableListOf<AdtTeamLists>()

    /* 리스트 초기화*/
    internal fun clearItems() {
        teamList.clear()
        notifyDataSetChanged()
    }

    override fun getCount() = teamList.size

    private class RecordTeamHolder {
        lateinit var recordList: View
        lateinit var teamEmblem: ImageView
        lateinit var teamName: TextView
        lateinit var winCount: TextView
        lateinit var drawCount: TextView
        lateinit var loseCount: TextView
        lateinit var winningRate: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val context = parent.context
        val holder: RecordTeamHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.item_team_record, parent, false)

            holder = RecordTeamHolder().apply {
                recordList = cv.lv_play_list
                teamEmblem = cv.iv_team_emblem
                teamName = cv.tv_team_name
                winCount = cv.tv_win
                drawCount = cv.tv_draw
                loseCount = cv.tv_lose
                winningRate = cv.tv_rate
            }

            cv.tag = holder

        } else {
            holder = convertView.tag as RecordTeamHolder
            cv = convertView
        }

        val teamItems = teamList[position]

        /* 테이블 헤더 레이블*/
        if (teamItems.win == LABEL_CODE) {
            holder.winCount.text = context.resources.getString(R.string.win)
            holder.drawCount.text = context.resources.getString(R.string.draw)
            holder.loseCount.text = context.resources.getString(R.string.lose)
            holder.winningRate.text = "%"
        } else {
            /* 팀 엠블럼*/
            holder.teamEmblem.setImageResource(teamItems.teamEmblem)

            /* 팀 이름*/
            holder.teamName.text = PrTeam.getTeamByCode(teamItems.team).fullName

            /* 승*/
            holder.winCount.text = teamItems.win.toString()

            /* 무*/
            holder.drawCount.text = teamItems.draw.toString()

            /* 패*/
            holder.loseCount.text = teamItems.lose.toString()

            /* 승률*/
            holder.winningRate.text = teamItems.rate.toString()
        }

        /* 상세보기*/
        cv.setOnClickListener { nav.getDetailLists(teamItems.year, teamItems.team) }

        return cv
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItem(position: Int) = teamList[position]

    /* 아이템 추가*/
    internal fun addItem(recordItem: AdtTeamLists) {
        teamList.add(recordItem)
    }

    internal fun addItem() {
        val labelCode = LABEL_CODE

        teamList.add(
            AdtTeamLists(
                year = 0,
                team = "",
                win = labelCode,
                draw = labelCode,
                lose = labelCode,
                rate = labelCode,
                teamEmblem = labelCode
            )
        )
    }

    companion object {
        private val LABEL_CODE = 9999
    }
}
