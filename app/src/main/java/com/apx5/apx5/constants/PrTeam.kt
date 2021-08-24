package com.apx5.apx5.constants

/**
 * 팀정보
 * @return PrTeam
 */

enum class PrTeam(
    val code: String,
    val fullName: String,
    val abbrName: String,
    val mainColor: String,
    val emblem: String
) {

    KAT("kat", "기아타이거즈", "기아", "#AF1D2B", "ic_team_kat"),
    DSB("dsb", "두산베어스", "두산", "#131230", "ic_team_dsb"),
    LTG("ltg", "롯데자이언츠", "롯데", "#DD0330", "ic_team_ltg"),
    NCD("ncd", "NC다이노스", "NC", "#1D467C", "ic_team_ncd"),
    SKW("skw", "SK와이번스", "SK", "#EA002C", "ic_team_skw"),
    KWH("kwh", "키움히어로즈", "키움", "#830125", "ic_team_kwh"),
    LGT("lgt", "LG트윈스", "LG", "#BD0636", "ic_team_lgt"),
    HHE("hhe", "한화이글스", "한화", "#F37321", "ic_team_hhe"),
    SSL("ssl", "삼성라이온즈", "삼성", "#0064B2", "ic_team_ssl"),
    KTW("ktw", "KT위즈", "KT", "#231F20", "ic_team_ktw"),
    OTHER("", "", "", "", "");

    companion object {
        fun team(code: String) =
            when (code) {
                "kat" -> KAT
                "dsb" -> DSB
                "ltg" -> LTG
                "ncd" -> NCD
                "skw" -> SKW
                "kwh" -> KWH
                "lgt" -> LGT
                "hhe" -> HHE
                "ssl" -> SSL
                "ktw" -> KTW
                else -> OTHER
            }
    }
}
