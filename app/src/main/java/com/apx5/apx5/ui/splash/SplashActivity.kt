package com.apx5.apx5.ui.splash

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.databinding.ActivitySplashBinding
import com.apx5.apx5.ui.dashboard.DashBoardActivity
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.login.LoginActivity
import com.apx5.apx5.ui.utils.MaterialTools
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * SplashActivity
 */

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModel(): SplashViewModel {
        splashViewModel.setNavigator(this)
        return splashViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()

        /* 서버 상태검사*/
        getViewModel().checkServerStatus()
    }

    /* 서버 동작여부 검사*/
    override fun getServerWorkResult(alive: Boolean) {
        if (alive) {
            /* 로그인, 메인분기*/
            getViewModel().startSeeding()
        } else {
            /* 앱종료*/
            dialogForServerIsDead()
        }
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        getViewDataBinding().skLoading.visibility = View.GONE
    }

    /* 로그인 > DashBoard*/
    override fun switchToLogin() {
        val intentLogin = LoginActivity.newIntent(this@SplashActivity)
        startActivity(intentLogin)
        this.overridePendingTransition(0, 0)
        finish()
    }

    /* DashBoard*/
    override fun switchToDashBoard() {
        val intentStatics = DashBoardActivity.newIntent(this@SplashActivity)
        startActivity(intentStatics)
        finish()
    }

    /* Components*/
    private fun initComponent() {
        /* 상단상태바 색상강제적용*/
        MaterialTools.setSystemBarColor(this, R.color.p_navy_10)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /* APP 종료*/
    private fun onFinish() {
        finish()
    }

    /* 서버 미작동 다이얼로그*/
    private fun dialogForServerIsDead() {
        DialogActivity.dialogNoInternet(this, ::onFinish)
    }
}