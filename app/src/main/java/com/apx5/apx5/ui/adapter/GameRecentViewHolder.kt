package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_game_recent.view.*

/**
 * GameRecentViewHolder
 */
class GameRecentViewHolder(
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

        /* 경기결과 구분처리*/
        val (awayStyle, homeStyle) = when {
            game.awayScore > game.homeScore -> {
                /* 원정팀승*/
                R.style.TeamScoreWinTeam to R.style.TeamScoreLoseTeam
            }
            game.awayScore < game.homeScore -> {
                /* 홈팀승*/
                R.style.TeamScoreLoseTeam to R.style.TeamScoreWinTeam
            }
            else -> {
                /* 양팀 무승부*/
                R.style.TeamScoreLoseTeam to R.style.TeamScoreLoseTeam
            }
        }

        itemView.tv_away_score.setTextAppearance(awayStyle)
        itemView.tv_home_score.setTextAppearance(homeStyle)

        itemView.iv_team_emblem_away.setImageResource(
                UiUtils.getDrawableByName(context, game.awayEmblem.emblem)
        )

        itemView.iv_team_emblem_home.setImageResource(
                UiUtils.getDrawableByName(context, game.homeEmblem.emblem)
        )
    }

    companion object {
        fun create(context: Context, parent: ViewGroup): GameRecentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_game_recent, parent, false)
            return GameRecentViewHolder(context, view)
        }
    }
}
