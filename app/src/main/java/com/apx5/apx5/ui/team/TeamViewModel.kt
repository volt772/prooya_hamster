package com.apx5.apx5.ui.team

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.model.ResourcePostUser
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.utils.equalsExt
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * TeamViewModel
 */

class TeamViewModel(application: Application) :
    BaseViewModel<TeamNavigator>(application) {

    private val rmts: PrApi = remoteService

    /**
     * 사용자 정보 서버 저장
     */
    internal fun saveTeam(teamCode: String) {
        val email = PrefManager.getInstance(getApplication()).userEmail

        if (email != null && !email.equalsExt("")) {
            val resourcePostUser = ResourcePostUser(email, teamCode)

            rmts.postUser(resourcePostUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<PrApi.PostUser>() {
                        override fun onCompleted() { }

                        override fun onError(e: Throwable) { }

                        override fun onNext(user: PrApi.PostUser) {
                            /* 저장 후, 로컬 팀코드 저장*/
                            getNavigator()?.switchPageBySelectType()
                        }
                    })
        } else {
            getNavigator()?.vectoredRestart()
        }
    }
}

