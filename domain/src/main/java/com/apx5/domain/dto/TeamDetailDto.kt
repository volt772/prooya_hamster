package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrTeamDetail
import com.apx5.domain.ops.OpsTeamDetail
import com.google.gson.annotations.SerializedName

/**
 * TeamDetailDto
 * @desc 팀 경기상세
 */

data class TeamDetailDto(

    @SerializedName(PrNetworkKeys.GAMES)
    val _games: List<OpsTeamDetail> = emptyList()

) : PrTeamDetail {

    override val games: List<OpsTeamDetail>
        get() = _games
}
