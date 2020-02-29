package com.apx5.apx5.constants

/**
 * 팀정보
 * @return PrTeam
 */

enum class PrTeam(
    val fullName: String,
    val abbrName: String,
    val mainColor: String,
    val emblem: String) {

    KAT("기아타이거즈", "기아", "#AF1D2B", "ic_team_kat"),
    DSB("두산베어스", "두산", "#131230", "ic_team_dsb"),
    LTG("롯데자이언츠", "롯데", "#DD0330", "ic_team_ltg"),
    NCD("NC다이노스", "NC", "#1D467C", "ic_team_ncd"),
    SKW("SK와이번스", "SK", "#EA002C", "ic_team_skw"),
    NXH("키움히어로즈", "키움", "#830125", "ic_team_nxh"),
    LGT("LG트윈스", "LG", "#BD0636", "ic_team_lgt"),
    HHE("한화이글스", "한화", "#F37321", "ic_team_hhe"),
    SSL("삼성라이온즈", "삼성", "#0064B2", "ic_team_ssl"),
    KTW("KT위즈", "KT", "#231F20", "ic_team_ktw"),
    OTHER("", "", "", "");

    companion object {
        fun getTeamByCode(code: String): PrTeam {
            return when (code) {
                "kat" -> KAT
                "dsb" -> DSB
                "ltg" -> LTG
                "ncd" -> NCD
                "skw" -> SKW
                "nxh" -> NXH
                "lgt" -> LGT
                "hhe" -> HHE
                "ssl" -> SSL
                "ktw" -> KTW
                else -> OTHER
            }
        }
    }
}
