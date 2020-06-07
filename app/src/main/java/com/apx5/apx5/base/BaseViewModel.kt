package com.apx5.apx5.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import java.lang.ref.WeakReference

/**
 * BaseViewModel
 */

abstract class BaseViewModel<N>(application: Application) :
    AndroidViewModel(application) {

    private var navigator : WeakReference<N>? = null

    private val isLoading = ObservableField(false)

    fun setIsLoading(isLoading : Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getIsLoading() = isLoading

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator?.get()
}