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
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.ui.model.PlayLists
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_plays_all.view.*

/**
 * RecordAllAdapter
 */

class RecordAllAdapter internal constructor(private val ctx: Context, private val nav: RecordAllNavigator) : BaseAdapter() {

    private val playList = mutableListOf<PlayLists>()

    internal fun clearItems() {
        playList.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return playList.size
    }

    private class RecordAllHolder {
        lateinit var recordAll: View
        lateinit var teamEmblemMy: ImageView
        lateinit var teamEmblemVersus: ImageView
        lateinit var myScore: TextView
        lateinit var versusScore: TextView
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
                teamEmblemMy = cv.iv_all_team_emblem_my
                teamEmblemVersus = cv.iv_all_team_emblem_versus
                myScore = cv.tv_all_my_score
                versusScore = cv.tv_all_versus_score
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
        holder.teamEmblemMy.setImageResource(playItems.emblemMy)
        holder.teamEmblemVersus.setImageResource(playItems.emblemVs)

        /* 팀 스코어*/
        holder.myScore.text = playItems.scoreMy
        holder.versusScore.text = playItems.scoreVs

        /* 경기일*/
        holder.playDate.text = UiUtils.getDateToFull(playItems.playDate)

        /* 경기결과*/
        var result = ""
        when (playItems.playResult) {
            "w" -> {
                result = PrConstants.Codes.WIN
                holder.playResult.setTextColor(ContextCompat.getColor(ctx, R.color.green_A700))
            }
            "d" -> {
                result = PrConstants.Codes.DRAW
                holder.playResult.setTextColor(ContextCompat.getColor(ctx, R.color.brown_800))
            }
            "l" -> {
                result = PrConstants.Codes.LOSE
                holder.playResult.setTextColor(ContextCompat.getColor(ctx, R.color.red_85))
            }
            else -> {
            }
        }

        holder.playResult.text = result

        /* 삭제*/
        holder.recordAll.setOnLongClickListener {
            nav.delHistoryItem(playItems.playId, playItems.playSeason, playItems.playVersus, playItems.playResult)
            true
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
    internal fun addItem(
            playId: String,
            playYear: String,
            playVs: String,
            playResult: String,
            playDate: String,
            ptGet: String,
            ptLost: String,
            emblemMy: Int,
            emblemVs: Int) {
        val item = PlayLists()
        item.playId = playId
        item.playSeason = playYear
        item.playVersus = playVs
        item.playResult = playResult
        item.playDate = playDate
        item.scoreMy = ptGet
        item.scoreVs = ptLost
        item.emblemMy = emblemMy
        item.emblemVs = emblemVs
        playList.add(item)
    }
}
