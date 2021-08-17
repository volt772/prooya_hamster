package com.apx5.apx5.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * BaseViewModel
 */

abstract class BaseViewModel<N> : ViewModel() {

    private var navigator : WeakReference<N>? = null

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator?.get()
}
