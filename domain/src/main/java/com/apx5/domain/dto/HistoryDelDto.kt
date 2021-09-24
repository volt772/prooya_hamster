package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrDelHistory
import com.google.gson.annotations.SerializedName

/**
 * HistoryDelDto
 * @desc 경기삭제
 */

data class HistoryDelDto(

    @SerializedName(PrNetworkKeys.COUNT)
    val _count: Int = 0

) : PrDelHistory {

    override val count: Int
        get() = _count
}
