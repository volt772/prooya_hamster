package com.apx5.apx5.ui.recordteam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtDetailLists
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_record_detail.view.*


/**
 * RecordDetailAdapter
 */

class RecordDetailAdapter internal constructor(
        private val ctx: Context
) : BaseAdapter() {

    private val detailList = mutableListOf<AdtDetailLists>()

    /* 리스트 초기화*/
    internal fun clearItems() {
        detailList.clear()
        notifyDataSetChanged()
    }

    override fun getCount() = detailList.size

    private class RecordDetailHolder {
        lateinit var playRecent: View
        lateinit var playResult: TextView
        lateinit var awayEmblem: ImageView
        lateinit var homeEmblem: ImageView
        lateinit var awayScore: TextView
        lateinit var homeScore: TextView
        lateinit var playDate: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val context = parent.context
        val holder: RecordDetailHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cv = inflater.inflate(R.layout.item_record_detail, parent, false)
            holder = RecordDetailHolder().apply {
                playRecent = cv.lv_play_list
                playResult = cv.tv_game_result
                awayEmblem = cv.iv_team_emblem_away
                homeEmblem = cv.iv_team_emblem_home
                awayScore = cv.tv_away_score
                homeScore = cv.tv_home_score
                playDate = cv.tv_play_date
            }

            cv.tag = holder

        } else {
            holder = convertView.tag as RecordDetailHolder
            cv = convertView
        }

        val detailItems = detailList[position]

        /* 팀 스코어*/
        holder.awayScore.text = detailItems.awayScore.toString()
        holder.homeScore.text = detailItems.homeScore.toString()

        /* 경기일*/
        val playDate = UiUtils.getDateToReadableMonthDay(detailItems.playDate)
        val stadium = detailItems.stadium

        holder.playDate.text = "${playDate}\n${stadium}"

        holder.awayEmblem.setImageResource(UiUtils.getDrawableByName(context, detailItems.awayEmblem.emblem))
        holder.homeEmblem.setImageResource(UiUtils.getDrawableByName(context, detailItems.homeEmblem.emblem))

        /* 경기결과*/
        holder.playResult.backgroundTintList =  context.getColorStateList(detailItems.playResult.color)
        holder.playResult.text = detailItems.playResult.displayCodeEn

        return cv
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItem(position: Int) = detailList[position]

    /* 아이템 추가*/
    internal fun addItem(details: AdtDetailLists) {
        detailList.add(details)
    }
}
