package com.apx5.apx5.network.operation

import com.apx5.apx5.constants.PrStatus

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

}