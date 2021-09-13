package com.apx5.apx5.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.constants.PrWinningStatus
import com.apx5.apx5.databinding.ItemHistoriesBinding
import com.apx5.apx5.ui.utilities.PrUtils
import com.apx5.domain.dto.HistoriesUi
import kotlinx.android.synthetic.main.item_game_all.view.*

/**
 * HistoriesViewHolder
 */
class HistoriesViewHolder(
    val binding: ItemHistoriesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(histories: HistoriesUi?, prUtils: PrUtils) {

        histories?.let { game ->
            /* 경기일*/
            val playDate = prUtils.getDateToReadableMonthDay(game.playDate.toString())
            val stadium = PrStadium.stadium(game.stadium).abbrName

            /* 경기결과 구분처리*/
            val distinguishWinning = prUtils.distinguishWinning(game.awayScore, game.homeScore)
            val (awayStyle, homeStyle) = when (distinguishWinning) {
                PrWinningStatus.AWAY -> R.style.TeamScoreWinTeam to R.style.TeamScoreLoseTeam
                PrWinningStatus.HOME -> R.style.TeamScoreLoseTeam to R.style.TeamScoreWinTeam
                PrWinningStatus.BOTH -> R.style.TeamScoreLoseTeam to R.style.TeamScoreLoseTeam
            }

            itemView.apply {
                /* 팀 스코어*/
                tv_away_score.text = game.awayScore.toString()
                tv_home_score.text = game.homeScore.toString()

                /* 경기일*/
                tv_play_date.text = String.format("%s\n%s", playDate, stadium)

                tv_away_score.setTextAppearance(awayStyle)
                tv_home_score.setTextAppearance(homeStyle)

                iv_team_emblem_away.setImageResource(
                    prUtils.getDrawableByName(context, PrTeam.team(game.awayTeam).emblem)
                )

                iv_team_emblem_home.setImageResource(
                    prUtils.getDrawableByName(context, PrTeam.team(game.homeTeam).emblem)
                )

                /* 경기결과*/
                val gameResult = PrResultCode.getResultByDisplayCode(game.playResult)
                tv_game_result.apply {
                    backgroundTintList =  context.getColorStateList(gameResult.color)
                    text = prUtils.getYear(game.playDate.toString())
                }
            }
        }
    }
}