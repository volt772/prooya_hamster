package com.apx5.apx5.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.databinding.ActivitySplashBinding
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.dashboard.DashBoardActivity
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.login.LoginActivity
import com.apx5.apx5.ui.utils.MaterialTools
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * SplashActivity
 */

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    private val svm: SplashViewModel by viewModels()

    override fun getLayoutId() = R.layout.activity_splash
    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()
        subscriber()
    }

    private fun subscriber() {
        svm.getServerStatus().observe(this, PrObserver {
            getServerWorkResult(it.status > 0)
            cancelSpinKit()
        })
    }

    /* 서버 동작여부 검사*/
    private fun getServerWorkResult(alive: Boolean) {
        if (alive) {
            /* 로그인, 메인분기*/
            seedingNextView()
        } else {
            /* 앱종료*/
            dialogForServerIsDead()
        }
    }

    private fun seedingNextView() {
        Handler().postDelayed({
            checkAccountAndDecideNextActivity()
        }, LOADING_DURATION)
    }


    /* Next Activity 검사*/
    private fun checkAccountAndDecideNextActivity() {
        prPreference.userEmail?.let { _email ->
            if (_email.isNotBlank() && _email.contains("@")) {
                switchToDashBoard()
            } else {
                switchToLogin()
            }
        } ?: run {
            switchToLogin()
        }
    }

    /* SpinKit 제거*/
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 로그인 > DashBoard*/
    private fun switchToLogin() {
        val intentLogin = LoginActivity.newIntent(this@SplashActivity)
        startActivity(intentLogin)
        this.overridePendingTransition(0, 0)
        finish()
    }

    /* DashBoard*/
    private fun switchToDashBoard() {
        val intentStatics = DashBoardActivity.newIntent(this@SplashActivity)
        startActivity(intentStatics)
        finish()
    }

    /* Components*/
    private fun initComponent() {
        /* 상단상태바 색상강제적용*/
        MaterialTools.setSystemBarColor(this, R.color.p_white_10)
    }

    /* APP 종료*/
    private fun onFinish() {
        finish()
    }

    /* 서버 미작동 다이얼로그*/
    private fun dialogForServerIsDead() {
        DialogActivity.dialogNoInternet(this, ::onFinish)
    }

    companion object {
        const val LOADING_DURATION = 1000L
    }
}