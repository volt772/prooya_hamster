package com.apx5.apx5.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * BaseViewModel
 */

abstract class BaseViewModel2<N> : ViewModel() {

    private var navigator : WeakReference<N>? = null

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator?.get()
}