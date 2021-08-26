package com.apx5.apx5.ext

/**
 * equalsExt
 * @desc null 과 공백("", " ", "/t"등) 문자열은 동일한 것으로 취급한다.
 */
fun String?.equalsExt(target: String?, ignoreCase: Boolean = false) : Boolean {

    if (this.isNullOrBlank()) {
        if (!target.isNullOrBlank()) return false
    } else {
        if (target.isNullOrBlank()) return false
        if (this.compareTo(target, ignoreCase) != 0) return false
    }

    return true
}

/**
 * splitExt
 * @desc 문자열을 리스트로 분리
 */
fun String?.splitExt(delimiter: String?) : List<String> {
    val emptyList = listOf<String>()

    if (this.isNullOrEmpty()) {
        if (!delimiter.isNullOrEmpty()) return emptyList
    } else {
        if (delimiter.isNullOrEmpty()) return emptyList

        return try {
            this.split(delimiter)
        } catch (e: Exception) {
            emptyList
        }
    }

    return emptyList
}