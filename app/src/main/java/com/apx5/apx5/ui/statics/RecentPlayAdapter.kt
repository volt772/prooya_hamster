package com.apx5.apx5.ui.statics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtPlayLists
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_plays.view.*

/**
 * RecentPlayAdapter
 */

class RecentPlayAdapter internal constructor() :
    BaseAdapter() {

    private val playList = mutableListOf<AdtPlayLists>()

    internal fun clearItems() {
        playList.clear()
        notifyDataSetChanged()
    }

    override fun getCount() = playList.size

    private class RecentPlayHolder {
        lateinit var playRecent: View
        lateinit var awayEmblem: ImageView
        lateinit var homeEmblem: ImageView
        lateinit var awayScore: TextView
        lateinit var homeScore: TextView
        lateinit var playDate: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val context = parent.context
        val holder: RecentPlayHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.item_plays, parent, false)

            holder = RecentPlayHolder().apply {
                playRecent = cv.lv_play_list
                awayEmblem = cv.iv_team_emblem_away
                homeEmblem = cv.iv_team_emblem_home
                awayScore = cv.tv_away_score
                homeScore = cv.tv_home_score
                playDate = cv.tv_play_date
            }

            cv.tag = holder
        } else {
            holder = convertView.tag as RecentPlayHolder
            cv = convertView
        }

        val playItems = playList[position]

        /* 팀 스코어*/
        holder.awayScore.text = playItems.awayScore.toString()
        holder.homeScore.text = playItems.homeScore.toString()

        /* 경기일*/
        val playDate = UiUtils.getDateToReadableMonthDay(playItems.playDate)
        val stadium = playItems.stadium

        holder.playDate.text = "${playDate}\n${stadium}"

        val awayEmblem: Int
        val homeEmblem: Int

        /* 경기결과 구분처리 (Bold and Emblem)*/
        when {
            playItems.awayScore > playItems.homeScore -> {
                /* 원정팀승*/
                holder.awayScore.setTextAppearance(R.style.TeamScoreWinTeam)
                holder.homeScore.setTextAppearance(R.style.TeamScoreLoseTeam)

                awayEmblem = UiUtils.getDrawableByName(context, playItems.awayEmblem.emblem)
                homeEmblem = UiUtils.getDrawableByName(context, playItems.homeEmblem.emblemBl)

            }
            playItems.awayScore < playItems.homeScore -> {
                /* 홈팀승*/
                holder.awayScore.setTextAppearance(R.style.TeamScoreLoseTeam)
                holder.homeScore.setTextAppearance(R.style.TeamScoreWinTeam)

                awayEmblem = UiUtils.getDrawableByName(context, playItems.awayEmblem.emblemBl)
                homeEmblem = UiUtils.getDrawableByName(context, playItems.homeEmblem.emblem)
            }
            else -> {
                /* 양팀 무승부*/
                holder.awayScore.setTextAppearance(R.style.TeamScoreLoseTeam)
                holder.homeScore.setTextAppearance(R.style.TeamScoreLoseTeam)

                awayEmblem = UiUtils.getDrawableByName(context, playItems.awayEmblem.emblem)
                homeEmblem = UiUtils.getDrawableByName(context, playItems.homeEmblem.emblem)
            }
        }

        holder.awayEmblem.setImageResource(awayEmblem)
        holder.homeEmblem.setImageResource(homeEmblem)

        return cv
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItem(position: Int) = playList[position]

    /* 아이템 추가*/
    internal fun addItem(play: AdtPlayLists) {
        playList.add(play)
    }
}
