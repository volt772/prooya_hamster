package com.apx5.apx5

import android.app.Application
import android.content.Context
import com.apx5.apx5.ui.login.kakao.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK
import dagger.hilt.android.HiltAndroidApp

/**
 * ProoyaClient
 */

@HiltAndroidApp
class ProoyaClient : Application() {
    /**
     * 이미지 로더를 반환한다.
     * @return 이미지 로더
     */
    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    companion object {
        @Volatile
        private var instance: ProoyaClient? = null

        lateinit var appContext: Context
    }
}
