package com.apx5.apx5.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.PrRemoteApplication
import java.lang.ref.WeakReference

/**
 * BaseViewModel
 */

abstract class BaseViewModel<N>(application: Application) :
    AndroidViewModel(application) {

    private var navigator : WeakReference<N>? = null

    private val isLoading = ObservableField<Boolean>(false)

    val remoteService: PrApi
        get() {
            val remoteApplication = PrRemoteApplication()
            return remoteApplication.remoteService
        }

    fun setIsLoading(isLoading : Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getIsLoading() = isLoading

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator?.get()
}