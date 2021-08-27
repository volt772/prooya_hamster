package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.ui.utilities.PrUtils
import com.apx5.apx5.ui.utilities.PrUtilsImpl
import kotlinx.android.synthetic.main.item_game_detail.view.*
import javax.inject.Inject

/**
 * GameDetailViewHolder
 */
class GameDetailViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {

    var prUtils: PrUtils = PrUtilsImpl()

    fun bind(game: AdtGames) {
        /* 경기일*/
        val playDate = prUtils.getDateToReadableMonthDay(game.playDate)
        val stadium = game.stadium

        itemView.apply {
            /* 팀 스코어*/
            tv_away_score.text = game.awayScore.toString()
            tv_home_score.text = game.homeScore.toString()

            /* 경기일*/
            tv_play_date.text = "${playDate}\n${stadium}"

            iv_team_emblem_away.setImageResource(prUtils.getDrawableByName(context, game.awayEmblem.emblem))
            iv_team_emblem_home.setImageResource(prUtils.getDrawableByName(context, game.homeEmblem.emblem))

            /* 경기결과*/
            tv_game_result.apply {
                backgroundTintList = context.getColorStateList(game.playResult.color)
                text = game.playResult.displayCodeEn
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): GameDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_game_detail, parent, false)
            return GameDetailViewHolder(view)
        }
    }
}