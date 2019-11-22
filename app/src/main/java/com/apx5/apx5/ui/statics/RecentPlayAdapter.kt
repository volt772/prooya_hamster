package com.apx5.apx5.ui.statics

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.apx5.apx5.R
import com.apx5.apx5.ui.model.PlayLists
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.equalsExt
import kotlinx.android.synthetic.main.item_plays.view.*

/**
 * RecentPlayAdapter
 */

class RecentPlayAdapter internal constructor() : BaseAdapter() {
    private val playList = mutableListOf<PlayLists>()

    internal fun clearItems() {
        playList.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return playList.size
    }

    private class RecentPlayHolder {
        lateinit var playRecent: View
        lateinit var myEmblem: ImageView
        lateinit var otherEmblem: ImageView
        lateinit var myScore: TextView
        lateinit var otherScore: TextView
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
                myEmblem = cv.iv_team_emblem_my
                otherEmblem = cv.iv_team_emblem_versus
                myScore = cv.tv_my_score
                otherScore = cv.tv_versus_score
                playDate = cv.tv_play_date
            }

            cv.tag = holder
        } else {
            holder = convertView.tag as RecentPlayHolder
            cv = convertView
        }

        val plyaItems = playList[position]

        /* 팀 엠블럼*/
        holder.myEmblem.setImageResource(plyaItems.emblemMy)
        holder.otherEmblem.setImageResource(plyaItems.emblemVs)

        /* 팀 스코어*/
        holder.myScore.text = plyaItems.scoreMy
        holder.otherScore.text = plyaItems.scoreVs

        /* 경기일*/
        holder.playDate.text = UiUtils.getDateToFull(plyaItems.playDate)

        /* 경기결과 구분처리 (Bold)*/
        if (plyaItems.playResult.equalsExt("w")) {
            holder.myScore.setTypeface(null, Typeface.BOLD)
        }
        if (plyaItems.playResult.equalsExt("l")) {
            holder.otherScore.setTypeface(null, Typeface.BOLD)
        }

        return cv
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return playList[position]
    }

    /* 아이템 추가*/
    internal fun addItem(playVersus: String, playResult: String, playDate: String, scoreMy: String, scoreVs: String, emblemMy: Int, emblemVs: Int) {
        val item = PlayLists()
        item.playVersus = playVersus
        item.playResult = playResult
        item.playDate = playDate
        item.scoreMy = scoreMy
        item.scoreVs = scoreVs
        item.emblemMy = emblemMy
        item.emblemVs = emblemVs
        playList.add(item)
    }
}
