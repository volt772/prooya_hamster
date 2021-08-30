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

    NCD("ncd", "NC다이노스", "NC", "#1D467C", "ic_team_ncd"),
    DSB("dsb", "두산베어스", "두산", "#131230", "ic_team_dsb"),
    KTW("ktw", "KT위즈", "KT", "#231F20", "ic_team_ktw"),
    LTG("ltg", "롯데자이언츠", "롯데", "#DD0330", "ic_team_ltg"),
    SKW("skw", "SK와이번스", "SK", "#EA002C", "ic_team_skw"),
    KWH("kwh", "키움히어로즈", "키움", "#830125", "ic_team_kwh"),
    HHE("hhe", "한화이글스", "한화", "#F37321", "ic_team_hhe"),
    KAT("kat", "기아타이거즈", "기아", "#AF1D2B", "ic_team_kat"),
    LGT("lgt", "LG트윈스", "LG", "#BD0636", "ic_team_lgt"),
    SSL("ssl", "삼성라이온즈", "삼성", "#0064B2", "ic_team_ssl"),
    OTHER("", "", "", "", "");

    companion object {
        fun team(code: String) =
            when (code) {
                "ncd" -> NCD
                "dsb" -> DSB
                "ktw" -> KTW
                "ltg" -> LTG
                "skw" -> SKW
                "kwh" -> KWH
                "hhe" -> HHE
                "kat" -> KAT
                "lgt" -> LGT
                "ssl" -> SSL
                else -> OTHER
            }
    }
}