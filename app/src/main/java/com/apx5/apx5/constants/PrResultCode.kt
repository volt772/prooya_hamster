package com.apx5.apx5.constants

import com.apx5.apx5.R

/**
 * 경기결과 코드
 * @return PrResultCode
 */

enum class PrResultCode(val code: Int, val displayCode: String, val color: Int) {
    WIN(987, "승", R.color.green_A700),
    DRAW(988, "무", R.color.brown_800),
    LOSE(989, "패", R.color.red_85),
    FINE(1000, "경기중", 0),
    OTHER(0, "", 0);

    companion object {
        fun getResultByCode(code: Int): PrResultCode {
            return when (code) {
                987 -> WIN
                988 -> DRAW
                989 -> LOSE
                1000 -> FINE
                else -> OTHER
            }
        }

        fun getResultByDisplayCode(code: String): PrResultCode {
            return when (code) {
                "w" -> WIN
                "d" -> DRAW
                "l" -> LOSE
                else -> OTHER
            }
        }
    }
}
