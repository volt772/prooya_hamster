package com.apx5.apx5.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.adapter.AdtPlays
import com.apx5.domain.ops.OpsTeamDetail
import com.apx5.apx5.ui.adapter.PrCentralAdapter
import com.apx5.apx5.ui.utilities.PrUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * DialogSeasonChange
 */

@AndroidEntryPoint
class DialogTeamDetail(
    private val plays: List<OpsTeamDetail>,
    val versus: String
): BottomSheetDialogFragment() {

    @Inject
    lateinit var prUtils: PrUtils

    private lateinit var prCentralAdapter: PrCentralAdapter

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
        tvVersusTitle.text = String.format("vs %s", PrTeam.team(versus).fullName)

        /* 상세내역 리스트*/
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        prCentralAdapter = PrCentralAdapter(requireContext(), PrAdapterViewType.DETAIL, prUtils)

        rvList?.apply {
            layoutManager = linearLayoutManager
            adapter = prCentralAdapter
        }

        val playList = mutableListOf<AdtPlays>().also { list ->
            for (play in plays) {
                list.add(
                    AdtPlays(
                        awayScore = play.awayScore,
                        awayEmblem = PrTeam.team(play.awayTeam),
                        homeScore = play.homeScore,
                        homeEmblem = PrTeam.team(play.homeTeam),
                        playResult = PrResultCode.getResultByDisplayCode(play.playResult),
                        playDate = "${play.playDate}",
                        stadium = PrStadium.stadium(play.stadium).abbrName
                    )
                )
            }
        }

        prCentralAdapter.apply {
            addPlays(playList)
            notifyDataSetChanged()
        }

        return view
    }
}