package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrStatics
import com.apx5.domain.ops.OpsAllStatics
import com.apx5.domain.ops.OpsTeamWinningRate
import com.apx5.domain.ops.OpsUser
import com.google.gson.annotations.SerializedName

/**
 * StaticsDto
 * @desc 요약데이터
 */

data class StaticsDto(

    @SerializedName(PrNetworkKeys.USER)
    val _user: OpsUser?= null,

    @SerializedName(PrNetworkKeys.ALL_STATICS)
    val _allStatics: OpsAllStatics?= null,

    @SerializedName(PrNetworkKeys.TEAM_WINNING_RATE)
    val _teamWinningRate: OpsTeamWinningRate?= null

) : PrStatics {

    override val user: OpsUser?
        get() = _user

    override val allStatics: OpsAllStatics?
        get() = _allStatics

    override val teamWinningRate: OpsTeamWinningRate?
        get() = _teamWinningRate
}