package com.apx5.apx5.ui.login.kakao

/**
 * Created by user on 2017-07-29.
 * 카카오 API
 */

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.ui.dashboard.DashBoardActivity
import com.apx5.apx5.ui.login.LoginActivity
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.ApiErrorCode
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.OptionalBoolean
import com.kakao.util.helper.log.Logger

class KakaoSignupActivity : Activity() {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMe()
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected fun requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {
                val message = "failed to get user info. msg=" + errorResult!!
                Logger.d(message)

                val result = errorResult.errorCode
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    finish()
                } else {
                    redirectLoginActivity()
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult) {
                Logger.e("onSessionClosed")
                redirectLoginActivity()
            }

            override fun onSuccess(result: MeV2Response) {

                if (result.hasSignedUp() == OptionalBoolean.FALSE) {
                    //
                } else {
                    setEmailToPref(result.kakaoAccount.email)
                    redirectDashBoard() // 로그인 성공시 MainActivity로
                }
            }
        })
    }

    private fun setEmailToPref(userEmail: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().putString(PrPrefKeys.MY_EMAIL, userEmail).commit()
    }

    private fun redirectDashBoard() {
        startActivity(Intent(this, DashBoardActivity::class.java))
        finish()
    }

    protected fun redirectLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }
}