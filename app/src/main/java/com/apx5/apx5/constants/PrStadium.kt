package com.apx5.apx5.constants

/**
 * 경기장
 * @return PrStadium
 */

enum class PrStadium(
    val displayName: String,
    val abbrName: String
) {

    SEOUL_JAMSIL("서울 잠실종합운동장 야구장", "서울잠실"),
    SEOUL_GOCHUK("서울 고척스카이돔", "서울고척"),
    KWANGJU("광주 기아챔피언스필드", "광주"),
    BUSAN("부산 사직야구장", "부산"),
    CHANGWON("창원 NC파크", "창원"),
    INCHEON("인천 문학경기장", "인천"),
    DAEJEON("대전 한화생명 이글스파크", "대전"),
    DAEGU("대구 삼성라이온즈파크", "대구"),
    SUWON("수원 KT위즈파크", "수원"),
    CHUNGJU("청주 종합운동장 야구장", "청주"),
    POHANG("포항 야구장", "포항"),
    ULSAN("울산 문수야구장", "울산"),
    OTHER("알수없음", "알수없음");

    companion object {
        fun stadium(code: String): PrStadium {
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