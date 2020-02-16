package com.apx5.apx5.ui.setting

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentSettingBinding
import com.apx5.apx5.model.ResourceDelUser
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.team.TeamActivity
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * SettingFragment
 */
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(), SettingNavigator, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private val settingViewModel: SettingViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun getViewModel(): SettingViewModel {
        settingViewModel.setNavigator(this)
        return settingViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    /* UI 초기화*/
    private fun initView() {
        val viewTeam = getViewDataBinding().tvTeam
        val viewNoti = getViewDataBinding().swtNoti
        val viewVersion = getViewDataBinding().tvVersion
        val viewChangeTeam = getViewDataBinding().lytChangeTeam
        val viewDelUser = getViewDataBinding().lytDelUser
        val viewLicense = getViewDataBinding().lytLicense

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

        /* Change Listener*/
        viewNoti.setOnCheckedChangeListener(this)
    }

    /* 계정삭제후, 앱재시작*/
    override fun vectoredRestart() {
        val packageManager = requireActivity().packageManager
        val intent = packageManager.getLaunchIntentForPackage(requireActivity().packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        requireActivity().startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    /* 로컬 데이터 초기화*/
    override fun clearSharedPreferences() {
        PrefManager.getInstance(requireContext()).removePref(requireContext())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.lyt_change_team -> {
                /* 팀변경*/
                val intentTeam = TeamActivity.newIntent(requireContext())
                intentTeam.putExtra("mode", "replaceTeam")
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

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (buttonView.id == R.id.swt_noti) {
            PrefManager.getInstance(requireContext()).setBoolean(PrPrefKeys.NOTIFICATION, isChecked)
        }
    }

    /* 사용자 원격삭제*/
    private fun delUserRemote() {
        val email = PrefManager.getInstance(requireContext()).userEmail

        if (email != null && !email.equalsExt("")) {
            val delUser = ResourceDelUser(email)
            getViewModel().delRemoteUser(delUser)
        }
    }

    /* 계정삭제 다이얼로그*/
    private fun showDelUserDialog() {
        DialogActivity.dialogUserDelete(requireContext(), ::delUserRemote)
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