package com.apx5.apx5.ui.calendar.ext

/**
 * 문자열을 서로 비교한다. null 과 공백("", " ", "/t"등) 문자열은 동일한 것으로 취급한다.
 */
fun String?.equalsExt(target: String?, ignoreCase: Boolean = false): Boolean {

    // 리시버가 null 이거나 공백
    if (this.isNullOrBlank()) {
        // 대상인 null 이거나 공백인지 확인한다
        if (!target.isNullOrBlank()) {
            return false
        }
    } else {
        // 리시버는 반드시 공백이 아닌 크기1이상의 문자열이다

        // 대상이 null 이거나 공백이다.
        if (target.isNullOrBlank()) return false

        // 리시버 및 비교 대상이 모두 null/공백이 아닌 크기가 1 이상인 문자열이다.
        if (this.compareTo(target, ignoreCase) != 0) {
            return false
        }
    }

    return true
}

/**
 * 문자열을 리스트로 분리
 */
fun String?.splitExt(delimiter: String?): List<String> {

    // 빈 리스트
    val emptyList = listOf<String>()

    if (this.isNullOrEmpty()) {
        // 구분자확인 null 인지 확인
        if (!delimiter.isNullOrEmpty()) {
            return emptyList
        }
    } else {
        // 구분자확인 null 인지 확인
        if (delimiter.isNullOrEmpty()) return emptyList

        return try {
            this.split(delimiter)
        } catch (e: Exception) {
            emptyList
        }
    }

    return emptyList
}
