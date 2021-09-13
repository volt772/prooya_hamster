package com.apx5.apx5.constants

/**
 * 시즌연도선택타입
 * @return PrDialogYearSelect
 */

enum class PrDialogYearSelectType(
    val displayName: String
) {
    SEASON("시즌선택"),
    SETTING("기본 시즌선택"),
    OTHER("시즌선택");
}