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
        /* 팀 스코어*/
        itemView.tv_away_score.text = game.awayScore.toString()
        itemView.tv_home_score.text = game.homeScore.toString()

        /* 경기일*/
        val playDate = UiUtils.getDateToReadableMonthDay(game.playDate)
        val stadium = game.stadium

        itemView.tv_play_date.text = "${playDate}\n${stadium}"

        val awayEmblem: Int
        val homeEmblem: Int

        /* 경기결과 구분처리 (Bold and Emblem)*/
        when {
            game.awayScore > game.homeScore -> {
                /* 원정팀승*/
                itemView.tv_away_score.setTextAppearance(R.style.TeamScoreWinTeam)
                itemView.tv_home_score.setTextAppearance(R.style.TeamScoreLoseTeam)

                awayEmblem = UiUtils.getDrawableByName(context, game.awayEmblem.emblem)
                homeEmblem = UiUtils.getDrawableByName(context, game.homeEmblem.emblemBl)

            }
            game.awayScore < game.homeScore -> {
                /* 홈팀승*/
                itemView.tv_away_score.setTextAppearance(R.style.TeamScoreLoseTeam)
                itemView.tv_home_score.setTextAppearance(R.style.TeamScoreWinTeam)

                awayEmblem = UiUtils.getDrawableByName(context, game.awayEmblem.emblemBl)
                homeEmblem = UiUtils.getDrawableByName(context, game.homeEmblem.emblem)
            }
            else -> {
                /* 양팀 무승부*/
                itemView.tv_away_score.setTextAppearance(R.style.TeamScoreLoseTeam)
                itemView.tv_home_score.setTextAppearance(R.style.TeamScoreLoseTeam)

                awayEmblem = UiUtils.getDrawableByName(context, game.awayEmblem.emblem)
                homeEmblem = UiUtils.getDrawableByName(context, game.homeEmblem.emblem)
            }
        }

        itemView.iv_team_emblem_away.setImageResource(awayEmblem)
        itemView.iv_team_emblem_home.setImageResource(homeEmblem)

        /* 경기결과*/
        itemView.tv_game_result.apply {
            backgroundTintList =  context.getColorStateList(game.playResult.color)
            text = game.playResult.displayCodeEn
        }

        /* 기록삭제 (Long Press)*/
        itemView.setOnLongClickListener {
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