package com.apx5.apx5.ui.splash

import android.app.Application
import android.os.Handler
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.dto.PrPingDto
import com.apx5.apx5.network.dto.PrStaticsDto
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.utils.equalsExt
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * TemplatesViewModel
 */

class SplashViewModel(application: Application) :
    BaseViewModel<SplashNavigator>(application) {

    private val prService = PrOps.getInstance()
    private val rmts: PrApi = remoteService

    /* 화면 표기 및 사용검사*/
    internal fun startSeeding() {
        val SPLASH_DISPLAY_LENGTH = 1000
        Handler().postDelayed({
            checkAccountAndDecideNextActivity()
        }, SPLASH_DISPLAY_LENGTH.toLong())
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
        prService.checkPing(object: PrOpsCallBack<PrPingDto> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrPingDto>?) {
                responseBody?.data?.let { res ->
                    getNavigator()?.getServerWorkResult(res.status > 0)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) {
                getNavigator()?.getServerWorkResult(false)
            }
        })
    }
}