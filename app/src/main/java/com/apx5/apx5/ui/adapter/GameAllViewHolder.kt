package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.android.synthetic.main.item_game_all.view.*

/**
 * GameAllViewHolder
 * @desc 게임관련 List 통합 Adapter
 */
class GameAllViewHolder(
    private val context: Context,
    view: View,
    private val delGame: ((AdtPlayDelTarget) -> Unit)?= null
): RecyclerView.ViewHolder(view) {

    fun bind(game: AdtGames) {
        /* 경기일*/
        val playDate = UiUtils.getDateToReadableMonthDay(game.playDate)
        val stadium = game.stadium

        /* 경기결과 구분처리*/
        val (awayStyle, homeStyle) = when {
            /* 원정팀승*/
            game.awayScore > game.homeScore -> R.style.TeamScoreWinTeam to R.style.TeamScoreLoseTeam
            /* 홈팀승*/
            game.awayScore < game.homeScore -> R.style.TeamScoreLoseTeam to R.style.TeamScoreWinTeam
            /* 양팀 무승부*/
            else -> R.style.TeamScoreLoseTeam to R.style.TeamScoreLoseTeam
        }

        itemView.apply {
            /* 팀 스코어*/
            tv_away_score.text = game.awayScore.toString()
            tv_home_score.text = game.homeScore.toString()

            /* 경기일*/
            tv_play_date.text = "${playDate}\n${stadium}"

            tv_away_score.setTextAppearance(awayStyle)
            tv_home_score.setTextAppearance(homeStyle)

            iv_team_emblem_away.setImageResource(
                UiUtils.getDrawableByName(context, game.awayEmblem.emblem)
            )

            iv_team_emblem_home.setImageResource(
                UiUtils.getDrawableByName(context, game.homeEmblem.emblem)
            )

            /* 경기결과*/
            tv_game_result.apply {
                backgroundTintList =  context.getColorStateList(game.playResult.color)
                text = game.playResult.displayCodeEn
            }

            /* 기록삭제 (Long Press)*/
            setOnLongClickListener {
                delGame?.let { _callback ->
                    _callback(
                        AdtPlayDelTarget(
                            id = game.playId?: 0,
                            season = game.playSeason?: 0,
                            versus = game.playVersus?: "",
                            result = game.playResult.codeAbbr
                        )
                    )
                }
                true
            }
        }
    }

    companion object {
        fun create(
            context: Context,
            parent: ViewGroup,
            delGame: ((AdtPlayDelTarget) -> Unit)?= null
        ): GameAllViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_game_all, parent, false)
            return GameAllViewHolder(context, view, delGame)
        }
    }
}