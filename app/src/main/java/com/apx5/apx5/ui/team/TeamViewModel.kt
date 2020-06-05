package com.apx5.apx5.ui.team

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtPostUser
import com.apx5.apx5.datum.pitcher.PtPostUser
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

    /**
     * 사용자 정보 서버 저장
     */
    internal fun saveTeam(teamCode: String) {
        val email = PrefManager.getInstance(getApplication()).userEmail
        if (email != null && !email.equalsExt("")) {
            prService.modifyUserInfo(PtPostUser(email, teamCode), object: PrOpsCallBack<CtPostUser> {
                override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostUser>?) {
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

