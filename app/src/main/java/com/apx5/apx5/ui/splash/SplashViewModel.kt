package com.apx5.apx5.ui.splash

import android.app.Application
import android.os.Handler
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtPing
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.utils.equalsExt

/**
 * TemplatesViewModel
 */

class SplashViewModel(application: Application) :
    BaseViewModel<SplashNavigator>(application) {

    private val prService = PrOps.getInstance()

    /* 화면 표기 및 사용검사*/
    internal fun startSeeding() {
        val DURATION = 1000
        Handler().postDelayed({
            checkAccountAndDecideNextActivity()
        }, DURATION.toLong())
    }

    /* Next Activity 검사*/
    private fun checkAccountAndDecideNextActivity() {
        val email = PrefManager.getInstance(getApplication()).userEmail

        if (email != null && !email.equalsExt("") && email.contains("@")) {
            if (!email.equalsExt("") && email.contains("@")) {
                getNavigator()?.switchToDashBoard()
            } else {
                getNavigator()?.switchToLogin()
            }
        } else {
            getNavigator()?.switchToLogin()
        }
    }

    /* 서버 검사*/
    internal fun checkServerStatus() {
        prService.checkPing(object: PrOpsCallBack<CtPing> {
            override fun onSuccess(
                responseCode: Int,
                responseMessage: String,
                responseBody: PrResponse<CtPing>?
            ) {
                responseBody?.data?.let { res ->
                    getNavigator()?.run {
                        getServerWorkResult(res.status > 0)
                        cancelSpinKit()
                    }
                }
            }

            override fun onFailed(errorData: PrOpsError) {
                getNavigator()?.getServerWorkResult(false)
            }
        })
    }
}