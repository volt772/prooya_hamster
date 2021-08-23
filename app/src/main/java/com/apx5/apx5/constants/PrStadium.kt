package com.apx5.apx5.constants

/**
 * 경기장
 * @return PrStadium
 */

enum class PrStadium(
    val displayName: String
) {

    SEOUL_JAMSIL("서울잠실"),
    SEOUL_GOCHUK("서울고척"),
    KWANGJU("광주"),
    BUSAN("부산"),
    CHANGWON("창원"),
    INCHEON("인천"),
    DAEJEON("대전"),
    DAEGU("대구"),
    SUWON("수원"),
    CHUNGJU("청주"),
    POHANG("포항"),
    ULSAN("울산"),
    OTHER("알수없음");

    companion object {
        fun getStadiumByCode(code: String): PrStadium {
            return when (code) {
                "soj" -> SEOUL_JAMSIL
                "sog" -> SEOUL_GOCHUK
                "kjc" -> KWANGJU
                "bss" -> BUSAN
                "cwn" -> CHANGWON
                "icl" -> INCHEON
                "dje" -> DAEJEON
                "dgl" -> DAEGU
                "sww" -> SUWON
                "cjj" -> CHUNGJU
                "poh" -> POHANG
                "uls" -> ULSAN
                else -> OTHER
            }
        }
    }
}
