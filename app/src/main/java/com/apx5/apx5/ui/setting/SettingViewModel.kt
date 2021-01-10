package com.apx5.apx5.ui.setting

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.pitcher.PtDelUser
import com.apx5.apx5.datum.catcher.CtDelUser
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse

/**
 * SettingViewModel
 */

class SettingViewModel(application: Application) :
    BaseViewModel<SettingNavigator>(application) {

    private val prService = PrOps.getInstance()

    /* 사용자 삭제 (Remote)*/
    internal fun delRemoteUser(delUser: PtDelUser) {
        prService.deleteUserInfo(delUser, object: PrOpsCallBack<CtDelUser> {
            override fun onSuccess(
                responseCode: Int,
                responseMessage: String,
                responseBody: PrResponse<CtDelUser>?
            ) {
                responseBody?.data?.let {
                    /* 사용자 삭제 결과*/
                    getNavigator()?.run {
                        clearSharedPreferences()
                        vectoredRestart()
                    }
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }
}
