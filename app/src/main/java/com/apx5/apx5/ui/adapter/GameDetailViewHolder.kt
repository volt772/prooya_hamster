package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_game_detail.view.*

/**
 * GameDetailViewHolder
 */
class GameDetailViewHolder(
    private val context: Context,
    view: View
): RecyclerView.ViewHolder(view) {

    fun bind(game: AdtGames) {
        /* 팀 스코어*/
        itemView.tv_away_score.text = game.awayScore.toString()
        itemView.tv_home_score.text = game.homeScore.toString()

        /* 경기일*/
        val playDate = UiUtils.getDateToReadableMonthDay(game.playDate)
        val stadium = game.stadium

        itemView.tv_play_date.text = "${playDate}\n${stadium}"

        itemView.iv_team_emblem_away.setImageResource(UiUtils.getDrawableByName(context, game.awayEmblem.emblem))
        itemView.iv_team_emblem_home.setImageResource(UiUtils.getDrawableByName(context, game.homeEmblem.emblem))

        /* 경기결과*/
        itemView.tv_game_result.apply {
            backgroundTintList = context.getColorStateList(game.playResult.color)
            text = game.playResult.displayCodeEn
        }
    }

    companion object {
        fun create(context: Context, parent: ViewGroup): GameDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_game_detail, parent, false)
            return GameDetailViewHolder(context, view)
        }
    }
}
