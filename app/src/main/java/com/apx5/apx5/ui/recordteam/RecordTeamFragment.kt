package com.apx5.apx5.ui.recordteam

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentRecordTeamBinding
import com.apx5.apx5.db.entity.PrTeamEntity
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * RecordTeamFragment
 */

class RecordTeamFragment : BaseFragment<FragmentRecordTeamBinding, RecordTeamViewModel>(), RecordTeamNavigator {

    private val recordTeamViewModel: RecordTeamViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_record_team
    }

    override fun getViewModel(): RecordTeamViewModel {
        recordTeamViewModel.setNavigator(this)
        return recordTeamViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    private var teamCode: String
    private lateinit var recordTeamAdapter: RecordTeamAdapter
    private lateinit var recordDetailAdapter: RecordDetailAdapter

    init {
        teamCode = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamCode = PrefManager.getInstance(requireContext()).userTeam?: ""

        if (teamCode.equalsExt("")) {
            DialogActivity.dialogError(requireContext())
        }

        initView()
        subscriber(UiUtils.currentYear)
    }

    /* UI 초기화*/
    private fun initView() {
        /* 팀리스트*/
        val teamList = getViewDataBinding().lvTeamLists
        recordTeamAdapter = RecordTeamAdapter(this)
        teamList.adapter = recordTeamAdapter

        /* 상세리스트*/
        recordDetailAdapter = RecordDetailAdapter(requireContext())

        /* 시즌변경 버튼*/
        val seasonChange = getViewDataBinding().btChangeSeason
        seasonChange.setOnClickListener {
            val seasonSelectDialog = SeasonSelectDialog.getInstance(this)
            seasonSelectDialog.show(childFragmentManager, "selectSeason")
        }
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        getViewDataBinding().skLoading.visibility = View.GONE
    }

    /* 시즌선택*/
    override fun selectSeasonYear(year: Int) {
        subscriber(year)
    }

    /* 상세정보 데이터*/
    override fun getDetailLists(year: String, versus: String) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (!email.equalsExt("")) {
            getViewModel().getDetailList(email, versus, Integer.parseInt(year))
        }
    }

    /* 상세정보 Dialog*/
    override fun showDetailLists(plays: List<HashMap<String, String>>) {
        recordDetailAdapter.clearItems()
        if (plays.isNotEmpty()) {
            for (play in plays) {
                val teamEmblem = getTeamEmblem(play["playVs"]?: "")
                recordDetailAdapter.addItem(
                    teamEmblem,
                    play["ptGet"]?: "",
                    play["ptLost"]?: "",
                    play["playDate"]?: "",
                    play["playResult"]?: "",
                    play["playVs"]?: ""
                )
            }

            recordDetailAdapter.notifyDataSetChanged()

            val builder = AlertDialog.Builder(requireContext())
            val inflater = layoutInflater
            val view = inflater.inflate(R.layout.dlg_record_detail, null)
            builder.setView(view)

            val listview = view.findViewById<ListView>(R.id.lv_record_list)
            val dialog = builder.create()

            listview.adapter = recordDetailAdapter
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        } else {
            DialogActivity.dialogNoRecordDetail(requireContext())
        }
    }

    /* 팀 엠블럼*/
    private fun getTeamEmblem(code: String): Int {
        if (!code.equalsExt("")) {
            return resources.getIdentifier(PrConstants.Teams.EMBLEM_PREFIX.plus(code), "drawable", activity!!.packageName)
        }

        return 0
    }

    /* 팀 기록 리스트*/
    override fun setTeamRecord(teams: List<PrTeamEntity>) {
        recordTeamAdapter.clearItems()

        recordTeamAdapter.addItem()
        for (team in teams) {
            if (teamCode != team.team) {
                recordTeamAdapter.addItem(
                    team.year,
                    team.team,
                    team.win,
                    team.draw,
                    team.lose,
                    team.rate,
                    getTeamEmblem(team.team)
                )
            }
        }

        recordTeamAdapter.notifyDataSetChanged()
    }

    /* 상단 헤더 요약*/
    override fun setHeaderSummary(summary: HashMap<String, Int>) {
        val userSummary = String.format(Locale.getDefault(), resources.getString(R.string.w_d_l), summary["win"], summary["draw"], summary["lose"])
        getViewDataBinding().tvSeasonStatic.text = userSummary

        val teamName = PrTeam.getTeamByCode(teamCode).fullName
        val teamEmblem = resources.getIdentifier(PrConstants.Teams.EMBLEM_PREFIX.plus(teamCode), "drawable", requireActivity().packageName)

        getViewDataBinding().tvTeamName.text = teamName
        getViewDataBinding().tvSeasonLabel.text = String.format(Locale.getDefault(), resources.getString(R.string.season_label), summary["year"])
        getViewDataBinding().ivTeamEmblem.setImageResource(teamEmblem)
    }

    /* Observers*/
    private fun subscriber(year: Int) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (email.equalsExt("")) {
            DialogActivity.dialogError(requireContext())
        } else {
            getViewModel().getTeams(email, year)
        }
    }

    companion object {

        fun newInstance(): RecordTeamFragment {
            val args = Bundle()
            val fragment = RecordTeamFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
