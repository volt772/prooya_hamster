package com.apx5.apx5.datum

import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam

data class DtGameInfo(
    var awayScore: Int,
    var homeScore: Int,
    var awayTeam: PrTeam,
    var homeTeam: PrTeam,
    var playDate: String,
    var stadium: PrStadium,
    var playTime: String,
    var statusCode: Int,
    var status: PrGameStatus
)