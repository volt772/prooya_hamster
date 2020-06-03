package com.apx5.apx5.network.dto

import com.apx5.apx5.remote.RemoteDailyPlay
import com.google.gson.annotations.SerializedName

/**
 * 오늘 내팀 경기목록
 */

open class PrGameDto {
    @SerializedName("games")
    val games: List<RemoteDailyPlay> = emptyList()
}
