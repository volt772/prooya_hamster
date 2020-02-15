package com.apx5.apx5.constants

/**
 * 경기장
 * @return PrStadium
 */

enum class PrStadium(val code: String, val displayName: String) {
    SEOUL_JAMSIL("soj", "서울잠실"),
    SEOUL_GOCHUK("sog", "서울고척"),
    KWANGJU("kjc", "광주"),
    BUSAN("bss", "부산"),
    CHANGWON("msg", "창원"),
    INCHEON("ich", "인천"),
    DAEJEON("dje", "대전"),
    DAEGU("dgl", "대구"),
    SUWON("sww", "수원"),
    CHUNGJU("cjj", "청주"),
    POHANG("poh", "포항"),
    ULSAN("uls", "울산"),
    OTHER("", "");

    companion object {
        fun getStadiumByCode(code: String): PrStadium {
            return when (code) {
                "soj" -> SEOUL_JAMSIL
                "sog" -> SEOUL_GOCHUK
                "kjc" -> KWANGJU
                "bss" -> BUSAN
                "msg" -> CHANGWON
                "ich" -> INCHEON
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
