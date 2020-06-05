package com.apx5.apx5.constants

/**
 * PrNetworks
 */

object PrNetworks {
    /* 기본 Response Key*/
    const val RESPONSE_CODE = "responseCode"
    const val RESPONSE_MESSAGE = "responseMessage"
    const val DATA = "data"

    object StatusMessage {
        /* 상태 Key*/
        const val OK = "OK"
        const val CREATED = "Created"
        const val ACCEPTED = "Accepted"
        const val NON_AUTH = "Non-Authoritative Information"
        const val BAD_REQUEST = "Bad Request"
        const val UNAUTHORIZED = "Unauthorized"
        const val NOT_FOUND = "Not Found"
    }
}
