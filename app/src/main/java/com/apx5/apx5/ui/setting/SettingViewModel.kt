package com.apx5.apx5.ui.setting

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.model.ResourceDelUser
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.dto.PrUserDelDto
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
    private val rmts: PrApi = remoteService

    /* 사용자 삭제 (Remote)*/
    internal fun delRemoteUser(delUser: ResourceDelUser) {
        prService.deleteUserInfo(delUser, object: PrOpsCallBack<PrUserDelDto> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrUserDelDto>?) {
                responseBody?.data?.let {
                    /* 사용자 삭제 결과*/
                    getNavigator()?.clearSharedPreferences()
                    getNavigator()?.vectoredRestart()
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }
}
