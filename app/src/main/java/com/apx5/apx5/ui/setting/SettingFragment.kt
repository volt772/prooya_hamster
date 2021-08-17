package com.apx5.apx5.ui.setting

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.*
import com.apx5.apx5.databinding.FragmentSettingBinding
import com.apx5.apx5.datum.pitcher.PtDelUser
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.team.TeamActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * SettingFragment
 */
class SettingFragment :
    BaseFragment<FragmentSettingBinding>(),
    View.OnClickListener {

    private val svm: SettingViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_setting
    override fun getBindingVariable() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
        val viewTeam = binding().tvTeam
        val viewVersion = binding().tvVersion
        val viewChangeTeam = binding().lytChangeTeam
        val viewDelUser = binding().lytDelUser
        val viewLicense = binding().lytLicense

        /* 팀명*/
        val teamCode = PrefManager.getInstance(requireContext()).getString(PrPrefKeys.MYTEAM, "")
        teamCode?.let { code ->
            viewTeam.text = PrTeam.getTeamByCode(code).fullName
        }

        /* 버전*/
        try {
            val manager = requireActivity().packageManager
            val info = manager.getPackageInfo(requireActivity().packageName, 0)
            viewVersion.text = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        /* Click Listener*/
        viewTeam.setOnClickListener(this)
        viewLicense.setOnClickListener(this)
        viewChangeTeam.setOnClickListener(this)
        viewDelUser.setOnClickListener(this)
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
        PrefManager.getInstance(requireContext()).removePref(requireContext())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lyt_change_team -> {
                /* 팀변경*/
                val intentTeam = TeamActivity.newIntent(requireContext())
                intentTeam.putExtra(PrConstants.Teams.TEAM_CHANGE_MODE, PrTeamChangeMode.CHANGE)
                startActivity(intentTeam)
            }
            R.id.lyt_del_user ->
                /* 사용자 삭제*/
                showDelUserDialog()
            R.id.lyt_license -> {
                /* 오픈라이선스*/
                val intentLicense = LicenseActivity.newIntent(requireContext())
                startActivity(intentLicense)
            }
        }
    }

    /* 사용자 원격삭제*/
    private fun delUserRemote() {
        val email = PrefManager.getInstance(requireContext()).userEmail

        email?.let {
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
        svm.getDelUserResult().observe(viewLifecycleOwner, {
            when (it.status) {
                PrStatus.SUCCESS -> {
                    clearSharedPreferences()
                    vectoredRestart()
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> {  }
            }
        })
    }

    companion object {
        fun newInstance(): SettingFragment {
            val args = Bundle()
            val fragment = SettingFragment()
            fragment.arguments = args
            return fragment
        }
    }
}