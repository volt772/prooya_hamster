package com.apx5.apx5.ui.splash

import android.app.Application
import android.os.Handler
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.utils.equalsExt
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * TemplatesViewModel
 */

class SplashViewModel(application: Application) : BaseViewModel<SplashNavigator>(application) {
    private val rmts: RemoteService = remoteService

    /* 화면 표기 및 사용검사*/
    internal fun startSeeding() {
        val SPLASH_DISPLAY_LENGTH = 1000
        Handler().postDelayed({ checkAccountAndDecideNextActivity() }, SPLASH_DISPLAY_LENGTH.toLong())
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
        rmts.appPing()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.Ping>() {
                override fun onCompleted() {
                    getNavigator()?.getServerWorkResult(true)
                    getNavigator()?.cancelSpinKit()
                }

                override fun onError(e: Throwable) {
                    getNavigator()?.getServerWorkResult(false)
                }

                override fun onNext(ping: RemoteService.Ping) {
                    getNavigator()?.getServerWorkResult(ping.res)
                }
            })
    }
}