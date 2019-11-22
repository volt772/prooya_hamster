package com.apx5.apx5.ui.setting

import android.app.Application

import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.model.ResourceDelUser

import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * SettingViewModel
 */

class SettingViewModel(application: Application) : BaseViewModel<SettingNavigator>(application) {
    private val rmts: RemoteService = remoteService

    /* 사용자 삭제 (Remote)*/
    internal fun delRemoteUser(delUser: ResourceDelUser) {
        rmts.delUser(delUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.DelUser>() {
                override fun onCompleted() {}

                override fun onError(e: Throwable) {}

                override fun onNext(deleted: RemoteService.DelUser) {
                    /* 사용자 삭제 결과*/
                    getNavigator()?.clearSharedPreferences()
                    getNavigator()?.vectoredRestart()
                }
            })
    }
}
