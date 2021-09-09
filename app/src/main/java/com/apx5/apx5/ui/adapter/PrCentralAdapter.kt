package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.datum.adapter.*
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
    private val selectGame: ((Int, String) -> Unit)?= null,
    private val selectTeam: ((AdtTeamSelection) -> Unit)?= null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /* List : Play*/
    private val plays : ArrayList<AdtPlays> = ArrayList()

    /* List : Team Winning Rate*/
    private val teams: ArrayList<AdtTeamWinningRate> = ArrayList()

    /* List : Team Summary*/
    private val seasons: ArrayList<AdtTeamLists> = ArrayList()

    /* List : Licenses*/
    private val licenses: ArrayList<AdtLicenseLists> = ArrayList()

    /* List : Team Selection*/
    private val teamSelection: ArrayList<AdtTeamSelection> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_RECENT -> GameRecentViewHolder.create(parent, prUtils)
            VIEW_TYPE_DETAIL -> GameDetailViewHolder.create(parent, prUtils)
            VIEW_TYPE_WINNING_RATE -> TeamWinningRateViewHolder.create(parent, prUtils)
            VIEW_TYPE_SEASON -> SeasonViewHolder.create(parent, prUtils, selectGame)
            VIEW_TYPE_LICENSE -> LicensesViewHolder.create(parent, prUtils)
            VIEW_TYPE_TEAM_SELECT -> TeamSelectViewHolder.create(parent, prUtils, selectTeam)
            else ->  throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_RECENT -> (holder as GameRecentViewHolder).bind(plays[position])
            VIEW_TYPE_DETAIL -> (holder as GameDetailViewHolder).bind(plays[position])
            VIEW_TYPE_WINNING_RATE -> (holder as TeamWinningRateViewHolder).bind(teams[position])
            VIEW_TYPE_SEASON -> (holder as SeasonViewHolder).bind(seasons[position])
            VIEW_TYPE_LICENSE -> (holder as LicensesViewHolder).bind(licenses[position])
            VIEW_TYPE_TEAM_SELECT -> (holder as TeamSelectViewHolder).bind(teamSelection[position])
        }
    }

    override fun getItemViewType(position: Int) = when(viewType) {
        PrAdapterViewType.RECENT -> VIEW_TYPE_RECENT
        PrAdapterViewType.DETAIL -> VIEW_TYPE_DETAIL
        PrAdapterViewType.WINNING_RATE -> VIEW_TYPE_WINNING_RATE
        PrAdapterViewType.SEASON -> VIEW_TYPE_SEASON
        PrAdapterViewType.LICENSE -> VIEW_TYPE_LICENSE
        PrAdapterViewType.TEAM_SELECTION -> VIEW_TYPE_TEAM_SELECT
    }

    override fun getItemCount() =
        when (viewType) {
            PrAdapterViewType.WINNING_RATE -> teams.size
            PrAdapterViewType.SEASON -> seasons.size
            PrAdapterViewType.LICENSE -> licenses.size
            PrAdapterViewType.TEAM_SELECTION -> teamSelection.size

            PrAdapterViewType.RECENT,
            PrAdapterViewType.DETAIL -> plays.size
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
    fun addSeasons(seasons: List<AdtTeamLists>) {
        this.seasons.apply {
            clear()
            addAll(seasons)
        }
    }

    /* 라이센스 아이템 추가*/
    fun addLicenses(licenses: List<AdtLicenseLists>) {
        this.licenses.apply {
            clear()
            addAll(licenses)
        }
    }

    /* 팀 선택 아이템 추가*/
    fun addTeamSelection(teamSelection: List<AdtTeamSelection>) {
        this.teamSelection.apply {
            clear()
            addAll(teamSelection)
        }
    }

    companion object {
        const val VIEW_TYPE_RECENT = 1
        const val VIEW_TYPE_DETAIL = 2
        const val VIEW_TYPE_WINNING_RATE = 3
        const val VIEW_TYPE_SEASON = 4
        const val VIEW_TYPE_LICENSE = 5
        const val VIEW_TYPE_TEAM_SELECT = 6
    }
}