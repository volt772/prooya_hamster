package com.apx5.apx5.ui.recordall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.datum.adapter.AdtPlayLists
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_plays_all.view.*

/**
 * RecordAllAdapter
 */

class RecordAllAdapter internal constructor(
    private val ctx: Context,
    private val nav: RecordAllNavigator
) : BaseAdapter() {

    private val playList = mutableListOf<AdtPlayLists>()

    internal fun clearItems() {
        playList.clear()
        notifyDataSetChanged()
    }

    override fun getCount() = playList.size

    private class RecordAllHolder {
        lateinit var recordAll: View
        lateinit var teamEmblemAway: ImageView
        lateinit var teamEmblemHome: ImageView
        lateinit var awayScore: TextView
        lateinit var homeScore: TextView
        lateinit var playDate: TextView
        lateinit var playResult: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val context = parent.context
        val holder: RecordAllHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.item_plays_all, parent, false)

            holder = RecordAllHolder().apply {
                recordAll = cv.lv_play_list_all
                teamEmblemAway = cv.iv_all_team_emblem_away
                teamEmblemHome = cv.iv_all_team_emblem_home
                awayScore = cv.tv_all_away_score
                homeScore = cv.tv_all_home_score
                playDate = cv.tv_all_play_date
                playResult = cv.tv_all_result
            }

            cv.tag = holder
        } else {
            holder = convertView.tag as RecordAllHolder
            cv = convertView
        }

        val playItems = playList[position]

        /* 팀 엠블럼*/
//        holder.teamEmblemAway.setImageResource(playItems.awayEmblem)
//        holder.teamEmblemHome.setImageResource(playItems.homeEmblem)

        /* 팀 스코어*/
        holder.awayScore.text = playItems.awayScore.toString()
        holder.homeScore.text = playItems.homeScore.toString()

        /* 경기일*/
        holder.playDate.text = UiUtils.getDateToFull(playItems.playDate)

        /* 경기결과*/
        val result = PrResultCode.getResultByDisplayCode(playItems.playResult)
        holder.playResult.text = result.displayCode
        holder.playResult.setTextColor(ContextCompat.getColor(ctx, result.color))

        /* 삭제*/
        holder.recordAll.setOnLongClickListener {
            nav.delHistoryItem(AdtPlayDelTarget(
                id = playItems.playId,
                season = playItems.playSeason,
                versus = playItems.playVersus,
                result = playItems.playResult
            ))
            true
        }

        return cv
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItem(position: Int) = playList[position]

    /* 아이템 추가*/
    internal fun addItem(play: AdtPlayLists) {
        playList.add(play)
    }
}
