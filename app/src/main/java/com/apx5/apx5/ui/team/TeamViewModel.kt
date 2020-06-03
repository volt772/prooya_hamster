package com.apx5.apx5.ui.team

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.model.ResourcePostUser
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.dto.PrUserDto
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.utils.equalsExt

/**
 * TeamViewModel
 */

class TeamViewModel(application: Application) :
    BaseViewModel<TeamNavigator>(application) {

    private val prService = PrOps.getInstance()
    private val rmts: PrApi = remoteService

    /**
     * 사용자 정보 서버 저장
     */
    internal fun saveTeam(teamCode: String) {
        val email = PrefManager.getInstance(getApplication()).userEmail
        if (email != null && !email.equalsExt("")) {
            prService.modifyUserInfo(ResourcePostUser(email, teamCode), object: PrOpsCallBack<PrUserDto> {
                override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrUserDto>?) {
                    responseBody?.data?.let {
                        getNavigator()?.switchPageBySelectType()
                    }
                }

                override fun onFailed(errorData: PrOpsError) {
                    getNavigator()?.vectoredRestart()
                }
            })
        }
    }
}

