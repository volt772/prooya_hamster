package com.apx5.apx5.ui.recordteam

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
import com.apx5.apx5.ui.model.DetailLists
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_record_detail.view.*

/**
 * RecordDetailAdapter
 */

class RecordDetailAdapter internal constructor(private val ctx: Context) : BaseAdapter() {
    private val detailList = mutableListOf<DetailLists>()

    /* 리스트 초기화*/
    internal fun clearItems() {
        detailList.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return detailList.size
    }

    private class RecordDetailHolder {
        lateinit var detailList: View
        lateinit var teamEmblem: ImageView
        lateinit var gotScore: TextView
        lateinit var lostScore: TextView
        lateinit var playResult: TextView
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
                detailList = cv.lv_play_detail
                teamEmblem = cv.iv_team_emblem
                gotScore = cv.tv_get_score
                lostScore = cv.tv_lost_score
                playResult = cv.tv_result
                playDate = cv.tv_date
            }

            cv.tag = holder

        } else {
            holder = convertView.tag as RecordDetailHolder
            cv = convertView
        }

        val detailItems = detailList[position]

        /* 팀 엠블럼*/
        holder.teamEmblem.setImageResource(detailItems.emblemTeam)

        /* 득점*/
        holder.gotScore.text = detailItems.ptGet

        /* 실점*/
        holder.lostScore.text = detailItems.ptLost

        /* 결과*/
        val result = PrResultCode.getResultByDisplayCode(detailItems.playResult)
        holder.playResult.text = result.displayCode
        holder.playResult.setTextColor(ContextCompat.getColor(ctx, result.color))

        /* 일자*/
        holder.playDate.text = UiUtils.replaceText(detailItems.playDate, "-", ".")

        return cv
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return detailList[position]
    }

    /* 아이템 추가*/
    internal fun addItem(emblemTeam: Int, ptGet: String, ptLost: String, playDate: String, playResult: String, playVs: String) {
        val item = DetailLists()
        item.emblemTeam = emblemTeam
        item.ptGet = ptGet
        item.ptLost = ptLost
        item.playDate = playDate
        item.playResult = playResult
        item.playVs = playVs

        detailList.add(item)
    }
}
