package com.apx5.apx5.datum.adapter

import android.graphics.drawable.Drawable

/**
 * Adapter Data Class
 * @desc Tab : 0, 5
 * @desc 팀선택리스트
 */

data class AdtTeamSelection (
    val teamImage: Int,
    val teamEmblem: Drawable?,
    val teamName: String,
    val teamCode: String,
    val teamColor: Int
)
