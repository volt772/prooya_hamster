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
        /* 경기일*/
        val playDate = UiUtils.getDateToReadableMonthDay(game.playDate)
        val stadium = game.stadium

        itemView.apply {
            /* 팀 스코어*/
            tv_away_score.text = game.awayScore.toString()
            tv_home_score.text = game.homeScore.toString()

            /* 경기일*/
            tv_play_date.text = "${playDate}\n${stadium}"

            /* 경기결과 구분처리*/
            val (awayStyle, homeStyle) = when {
                /* 원정팀승*/
                game.awayScore > game.homeScore -> R.style.TeamScoreWinTeam to R.style.TeamScoreLoseTeam
                /* 홈팀승*/
                game.awayScore < game.homeScore -> R.style.TeamScoreLoseTeam to R.style.TeamScoreWinTeam
                /* 양팀 무승부*/
                else -> R.style.TeamScoreLoseTeam to R.style.TeamScoreLoseTeam
            }

            tv_away_score.setTextAppearance(awayStyle)
            tv_home_score.setTextAppearance(homeStyle)

            iv_team_emblem_away.setImageResource(UiUtils.getDrawableByName(context, game.awayEmblem.emblem))
            iv_team_emblem_home.setImageResource(UiUtils.getDrawableByName(context, game.homeEmblem.emblem))
        }
    }

    companion object {
        fun create(context: Context, parent: ViewGroup): GameRecentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_game_recent, parent, false)
            return GameRecentViewHolder(context, view)
        }
    }
}