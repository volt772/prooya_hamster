package com.apx5.apx5.constants

import com.apx5.apx5.R

/**
 * 경기결과 코드
 * @return PrResultCode
 */

enum class PrResultCode(
    val code: Int,
    val codeAbbr: String,
    val displayCode: String,
    val color: Int
) {

    WIN(987, "w", "승", R.color.green_A100),
    DRAW(988, "d", "무", R.color.brown_100),
    LOSE(989, "l", "패", R.color.red_A100),
    FINE(1000, "p",  "경기중", 0),
    OTHER(0, "", "", 0);

    companion object {
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
