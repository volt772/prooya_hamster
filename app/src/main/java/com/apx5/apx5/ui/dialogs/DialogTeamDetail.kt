package com.apx5.apx5.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.adapter.AdtDetailLists
import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.apx5.apx5.ui.recordteam.RecordDetailAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * DialogSeasonChange
 */

class DialogTeamDetail(
    private val plays: List<OpsTeamDetail>,
    val versus: String
): BottomSheetDialogFragment() {

    private lateinit var recordDetailAdapter: RecordDetailAdapter

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

        val lvRecord = view.findViewById<ListView>(R.id.lv_record_list)
        val tvVersusTitle = view.findViewById<TextView>(R.id.tv_versus_title)

        /* 상대팀명*/
        tvVersusTitle.text = "vs ${PrTeam.getTeamByCode(versus).fullName}"

        /* 상세내역 리스트*/
        recordDetailAdapter = RecordDetailAdapter(requireContext())
        lvRecord.adapter = recordDetailAdapter

        recordDetailAdapter.clearItems()
        for (play in plays) {
            recordDetailAdapter.addItem(
                AdtDetailLists(
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

        recordDetailAdapter.notifyDataSetChanged()
        return view
    }
}