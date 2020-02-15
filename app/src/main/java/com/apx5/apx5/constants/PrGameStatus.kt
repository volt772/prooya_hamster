package com.apx5.apx5.constants

/**
 * 경기 코드
 * @return PrGameStatus
 */

enum class PrGameStatus(val code: Int, val displayCode: String) {
    ONPLAY(997, "경기중"),
    STANDBY(998, "경기전"),
    CANCELED(999, "취소"),
    FINE(1000, "경기종료");

    companion object {
        fun getStatsByCode(code: Int): PrGameStatus {
            return when (code) {
                997 -> ONPLAY
                998 -> STANDBY
                999 -> CANCELED
                1000 -> FINE
                else -> STANDBY
            }
        }
    }
}