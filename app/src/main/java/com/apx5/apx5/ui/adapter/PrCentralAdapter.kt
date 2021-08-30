package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.datum.adapter.AdtPlays
import com.apx5.apx5.datum.adapter.AdtTeamLists
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.ui.utilities.PrUtils
import javax.inject.Inject

/**
 * PrCentralAdapter
 * @desc 통합 Adapter
 */
class PrCentralAdapter @Inject constructor(
    val context: Context,
    private val viewType: PrAdapterViewType,
    val prUtils: PrUtils,
    private val delGame: ((AdtPlayDelTarget) -> Unit)?= null,
    private val selectGame: ((Int, String) -> Unit)?= null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /* List : Play*/
    private val plays : ArrayList<AdtPlays> = ArrayList()

    /* List : Team Winning Rate*/
    private val teams: ArrayList<AdtTeamWinningRate> = ArrayList()

    /* List : Team Summary*/
    private val teamsSummary: ArrayList<AdtTeamLists> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_RECENT -> GameRecentViewHolder.create(parent, prUtils)
            VIEW_TYPE_DETAIL -> GameDetailViewHolder.create(parent, prUtils)
            VIEW_TYPE_ALL -> GameAllViewHolder.create(parent, prUtils, delGame)
            VIEW_TYPE_WINNING_RATE -> TeamWinningRateViewHolder.create(parent, prUtils)
            VIEW_TYPE_TEAM -> TeamSummaryViewHolder.create(parent, prUtils, selectGame)
            else ->  throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_RECENT -> (holder as GameRecentViewHolder).bind(plays[position])
            VIEW_TYPE_DETAIL -> (holder as GameDetailViewHolder).bind(plays[position])
            VIEW_TYPE_ALL -> (holder as GameAllViewHolder).bind(plays[position])
            VIEW_TYPE_WINNING_RATE -> (holder as TeamWinningRateViewHolder).bind(teams[position])
            VIEW_TYPE_TEAM -> (holder as TeamSummaryViewHolder).bind(teamsSummary[position])
        }
    }

    override fun getItemViewType(position: Int) = when(viewType) {
        PrAdapterViewType.RECENT -> VIEW_TYPE_RECENT
        PrAdapterViewType.DETAIL -> VIEW_TYPE_DETAIL
        PrAdapterViewType.ALL -> VIEW_TYPE_ALL
        PrAdapterViewType.WINNING_RATE -> VIEW_TYPE_WINNING_RATE
        PrAdapterViewType.TEAM -> VIEW_TYPE_TEAM
    }

    override fun getItemCount() =
        when (viewType) {
            PrAdapterViewType.WINNING_RATE -> teams.size

            PrAdapterViewType.TEAM -> teamsSummary.size

            PrAdapterViewType.RECENT,
            PrAdapterViewType.DETAIL,
            PrAdapterViewType.ALL -> plays.size
        }

    /* 경기 아이템 추가*/
    fun addPlays(plays: List<AdtPlays>) {
        this.plays.apply {
            clear()
            addAll(plays)
        }
    }

    /* 팀 승률 아이템 추가*/
    fun addTeamRate(team: List<AdtTeamWinningRate>) {
        this.teams.apply {
            clear()
            addAll(team)
        }
    }

    /* 팀 기록 아이템 추가*/
    fun addTeamSummary(teamSummary: List<AdtTeamLists>) {
        this.teamsSummary.apply {
            clear()
            addAll(teamSummary)
        }
    }

    companion object {
        const val VIEW_TYPE_RECENT = 1
        const val VIEW_TYPE_DETAIL = 2
        const val VIEW_TYPE_ALL = 3
        const val VIEW_TYPE_WINNING_RATE = 4
        const val VIEW_TYPE_TEAM = 5
    }
}