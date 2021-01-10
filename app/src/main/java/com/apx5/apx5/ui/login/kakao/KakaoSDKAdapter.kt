package com.apx5.apx5.ui.login.kakao

/**
 * Created by user on 2017-07-29.
 * 카카오 API
 */

import com.apx5.apx5.ProoyaClient
import com.kakao.auth.*

/**
 * @author leoshin on 15. 9. 15.
 */
class KakaoSDKAdapter : KakaoAdapter() {
    /**
     * Session Config에 대해서는 default값들이 존재한다.
     * 필요한 상황에서만 override해서 사용하면 됨.
     * @return Session의 설정값.
     */
    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_TALK)
            }

            override fun isSecureMode(): Boolean {
                return false
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSaveFormData(): Boolean {
                return true
            }
        }
    }

    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig { ProoyaClient.appContext }
    }

    companion object {
        protected val PROPERTY_DEVICE_ID = "device_id"
    }
}