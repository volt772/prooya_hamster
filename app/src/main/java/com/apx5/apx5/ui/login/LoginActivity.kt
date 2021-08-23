package com.apx5.apx5.ui.login

/**
 * Created by user on 2017-07-29.
 * 로그인
 */

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.apx5.apx5.R
import com.apx5.apx5.ui.login.kakao.KakaoSignupActivity
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger

/**
 * Created by hp on 2016-01-26.
 */
open class LoginActivity : Activity() {

    private var callback: SessionCallback? = null      //콜백 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        callback = SessionCallback()                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            redirectSignupActivity()  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Logger.e(exception)
            }
            redirectLoginActivity()
            setContentView(R.layout.activity_login) // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected fun redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        val intent = Intent(this, KakaoSignupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

    protected fun redirectLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}