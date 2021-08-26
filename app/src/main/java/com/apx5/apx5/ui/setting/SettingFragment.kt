package com.apx5.apx5.ui.setting

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrDialogYearSelectType
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrTeamChangeMode
import com.apx5.apx5.databinding.FragmentSettingBinding
import com.apx5.apx5.datum.pitcher.PtDelUser
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.dialogs.DialogSeasonChange
import com.apx5.apx5.ui.team.TeamActivity
import com.apx5.apx5.ui.listener.OnSingleClickListener
import com.apx5.apx5.ui.utils.UiUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * SettingFragment
 */

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    private val svm: SettingViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_setting
    override fun getBindingVariable() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
        /* 팀엠블럼*/
        prPreference.getString(PrPrefKeys.MY_TEAM, "")?.let { code ->
            binding().ivTeam.setImageResource(
                UiUtils.getDrawableByName(
                    requireContext(),
                    PrConstants.Teams.EMBLEM_PREFIX.plus(code)
                )
            )
        }
        /* 기본 시즌연도*/
        binding().acbDefaultYearMore.text = String.format("%d년", getDefaultYearFromPref())

        /* 버전*/
        try {
            val manager = requireActivity().packageManager
            val info = manager.getPackageInfo(requireActivity().packageName, 0)
            binding().tvSettingAppVersion.text = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        /* Click Events*/
        binding().apply {
            /* 팀변경*/
            clSettingGeneralMyTeam.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { setForChangeTeam() }
            })

            /* 사용자 삭제*/
            clSettingGeneralInitHistories.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { setForUserDelete() }
            })

            /* 라이선스*/
            clSettingAppLicense.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { setForOpenLicense() }
            })

            /* 기본 조회연도 선택(Const)*/
            clSettingGeneralDefaultYear.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { setDefaultYear() }
            })

            /* 기본 조회연도 선택(Btn)*/
            acbDefaultYearMore.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { setDefaultYear() }
            })
        }
    }

    /**
     * 팀변경
     */
    private fun setForChangeTeam() {
        val intentTeam = TeamActivity.newIntent(requireContext())
        intentTeam.putExtra(PrConstants.Teams.TEAM_CHANGE_MODE, PrTeamChangeMode.CHANGE)
        startActivity(intentTeam)
    }

    /**
     * 사용자 삭제
     */
    private fun setForUserDelete() {
        showDelUserDialog()
    }

    /**
     * 라이선스
     */
    private fun setForOpenLicense() {
        val intentLicense = LicenseActivity.newIntent(requireContext())
        startActivity(intentLicense)
    }

    /**
     * 기본 조회연도 선택
     */
    private fun setDefaultYear() {
        val seasonSelectDialog = DialogSeasonChange(
            callback = ::selectSeasonYear,
            selectedYear = getDefaultYearFromPref(),
            selectType = PrDialogYearSelectType.SETTING
        )

        seasonSelectDialog.show(childFragmentManager, "selectSeason")
    }

    /**
     * 시즌선택 (From BottomDialog)
     */
    private fun selectSeasonYear(year: Int) {
        setDefaultYearFromPref(year)
        binding().acbDefaultYearMore.text = String.format("%d년", year)
    }

    /**
     * 시즌로딩 (From Prefs)
     */
    private fun getDefaultYearFromPref() = prPreference.defaultYear

    /**
     * 시즌저장 (To Prefs)
     */
    private fun setDefaultYearFromPref(year: Int) {
        prPreference.setInt(PrPrefKeys.DEFAULT_SEASON_YEAR, year)
    }

    /* 계정삭제후, 앱재시작*/
    private fun vectoredRestart() {
        val packageManager = requireActivity().packageManager
        val intent = packageManager.getLaunchIntentForPackage(requireActivity().packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        requireActivity().startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    /* 로컬 데이터 초기화*/
    private fun clearSharedPreferences() {
        prPreference.removePref(requireContext())
    }

    /* 사용자 원격삭제*/
    private fun delUserRemote() {
        prPreference.userEmail?.let {
            if (it.isNotBlank()) {
                val delUser = PtDelUser(it)
                svm.delRemoteUser(delUser)
            }
        }
    }

    /* 계정삭제 다이얼로그*/
    private fun showDelUserDialog() {
        DialogActivity.dialogUserDelete(requireContext(), ::delUserRemote)
    }

    private fun subscriber() {
        svm.getDelUserResult().observe(viewLifecycleOwner, PrObserver {
            clearSharedPreferences()
            vectoredRestart()
        })
    }

    companion object {
        fun newInstance() = SettingFragment().apply {  }
    }
}