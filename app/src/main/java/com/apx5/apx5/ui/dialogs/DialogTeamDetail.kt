package com.apx5.apx5.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.apx5.apx5.ui.adapter.PlayItemsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * DialogSeasonChange
 */

class DialogTeamDetail(
    private val plays: List<OpsTeamDetail>,
    val versus: String
): BottomSheetDialogFragment() {

    private lateinit var playItemsAdapter: PlayItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TeamDetailRoundStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_record_detail, container, false)

        val rvList = view.findViewById<RecyclerView>(R.id.rv_list)
        val tvVersusTitle = view.findViewById<TextView>(R.id.tv_versus_title)

        /* 상대팀명*/
        tvVersusTitle.text = "vs ${PrTeam.getTeamByCode(versus).fullName}"

        /* 상세내역 리스트*/
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        playItemsAdapter = PlayItemsAdapter(requireContext(), PrAdapterViewType.DETAIL)

        rvList?.apply {
            layoutManager = linearLayoutManager
            adapter = playItemsAdapter
        }

        for (play in plays) {
            playItemsAdapter.addItem(
                AdtGames(
                    awayScore = play.awayScore,
                    awayEmblem = PrTeam.getTeamByCode(play.awayTeam),
                    homeScore = play.homeScore,
                    homeEmblem = PrTeam.getTeamByCode(play.homeTeam),
                    playResult = PrResultCode.getResultByDisplayCode(play.playResult),
                    playDate = "${play.playDate}",
                    stadium = PrStadium.getStadiumByCode(play.stadium).displayName
                )
            )
        }

        playItemsAdapter.notifyDataSetChanged()
        return view
    }
}