package com.apx5.domain.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * HistoriesUi
 */
@Parcelize
data class HistoriesUi (
    val awayScore: Int,
    val awayTeam: String,
    val homeScore: Int,
    val homeTeam: String,
    val playDate: Int,
    val playId: Int,
    val playResult: String,
    val playSeason: Int,
    val playVs: String,
    val stadium: String
): Parcelable