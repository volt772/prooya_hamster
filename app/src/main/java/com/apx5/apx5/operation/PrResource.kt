package com.apx5.apx5.operation

import androidx.lifecycle.Observer
import com.apx5.apx5.constants.PrStatus

/**
 * PrResource
 */
data class PrResource<out T>(val status: PrStatus, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): PrResource<T> {
            return PrResource(PrStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): PrResource<T> {
            return PrResource(PrStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): PrResource<T> {
            return PrResource(PrStatus.LOADING, data, null)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            data
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
//    fun peekContent(): T = data

}

class PrObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<PrResource<T>> {
    override fun onChanged(event: PrResource<T>?) {
        event?.let { _event ->
            when(_event.status) {
                PrStatus.SUCCESS -> {
                    _event.getContentIfNotHandled()?.let {
                        onEventUnhandledContent(it)
                    }
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> {}
            }
        }
    }
}
