package com.apx5.apx5.constants

/**
 * 경기 코드
 * @return PrGameStatus
 */

enum class PrGameStatus(
    val code: Int,
    val displayCode: String,
    val color: String
) {

    ONPLAY(997, "경기중", "#FFD54F"),
    STANDBY(998, "경기전", "#E57373"),
    CANCELED(999, "취소", "#A1887F"),
    FINE(1000, "경기종료", "#64B5F6");

    companion object {
        fun status(code: Int): PrGameStatus {
            return when (code) {
                997 -> ONPLAY
                998 -> STANDBY
                999 -> CANCELED
                1000 -> FINE
                else -> FINE
            }
        }
    }
}