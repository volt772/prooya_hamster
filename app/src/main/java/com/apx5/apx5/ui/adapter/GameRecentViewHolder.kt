package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrWinningStatus
import com.apx5.apx5.datum.adapter.AdtPlays
import com.apx5.apx5.ui.utilities.PrUtils
import kotlinx.android.synthetic.main.item_game_recent.view.*

/**
 * GameRecentViewHolder
 * @desc Statics, 최근 경기
 */
class GameRecentViewHolder(
    view: View,
    val prUtils: PrUtils
): RecyclerView.ViewHolder(view) {

    fun bind(game: AdtPlays) {
        /* 경기일*/
        val playDate = prUtils.getDateToReadableMonthDay(game.playDate)
        val stadium = game.stadium

        itemView.apply {
            /* 팀 스코어*/
            tv_away_score.text = game.awayScore.toString()
            tv_home_score.text = game.homeScore.toString()

            /* 경기일*/
            tv_play_date.text = String.format("%s\n%s", playDate, stadium)

            /* 경기결과 구분처리*/
            val distinguishWinning = prUtils.distinguishWinning(game.awayScore, game.homeScore)
            val (awayStyle, homeStyle) = when (distinguishWinning) {
                PrWinningStatus.AWAY -> R.style.TeamScoreWinTeam to R.style.TeamScoreLoseTeam
                PrWinningStatus.HOME -> R.style.TeamScoreLoseTeam to R.style.TeamScoreWinTeam
                PrWinningStatus.BOTH -> R.style.TeamScoreLoseTeam to R.style.TeamScoreLoseTeam
            }

            tv_away_score.setTextAppearance(awayStyle)
            tv_home_score.setTextAppearance(homeStyle)

            iv_team_emblem_away.setImageResource(prUtils.getDrawableByName(context, game.awayEmblem.emblem))
            iv_team_emblem_home.setImageResource(prUtils.getDrawableByName(context, game.homeEmblem.emblem))
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            prUtils: PrUtils
        ): GameRecentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_game_recent, parent, false)
            return GameRecentViewHolder(view, prUtils)
        }
    }
}