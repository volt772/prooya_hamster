package com.apx5.apx5.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.remote.RemoteApplication
import java.lang.ref.WeakReference

/**
 * BaseViewModel
 */

abstract class BaseViewModel<N>(application: Application) : AndroidViewModel(application) {

    private var navigator : WeakReference<N>? = null

    val isLoading = ObservableField<Boolean>(false)

    val remoteService: RemoteService
        get() {
            val remoteApplication = RemoteApplication()
            return remoteApplication.remoteService
        }

    fun setIsLoading(isLoading : Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getIsLoading() : ObservableField<Boolean> {
        return isLoading
    }

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() : N? {
        return navigator?.get()
    }


//    val isLoading = ObservableBoolean(false)
//
//    private var mNavigator: WeakReference<N>? = null
//
//    var navigator: N
//        get() = mNavigator!!.get()
//        set(navigator) {
//            this.mNavigator = WeakReference(navigator)
//        }
//
//
//    override fun onCleared() {
//        super.onCleared()
//    }
//
//    fun setIsLoading(isLoading: Boolean) {
//        this.isLoading.set(isLoading)
//    }
}