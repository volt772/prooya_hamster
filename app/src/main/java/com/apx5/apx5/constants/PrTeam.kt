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
    val emblem: String,
    val emblemBl: String) {

    KAT("kat", "기아타이거즈", "기아", "#AF1D2B", "ic_team_kat", "ic_team_kat_bl"),
    DSB("dsb", "두산베어스", "두산", "#131230", "ic_team_dsb", "ic_team_dsb_bl"),
    LTG("ltg", "롯데자이언츠", "롯데", "#DD0330", "ic_team_ltg", "ic_team_ltg_bl"),
    NCD("ncd", "NC다이노스", "NC", "#1D467C", "ic_team_ncd", "ic_team_ncd_bl"),
    SKW("skw", "SK와이번스", "SK", "#EA002C", "ic_team_skw", "ic_team_skw_bl"),
    NXH("nxh", "키움히어로즈", "키움", "#830125", "ic_team_nxh", "ic_team_nxh_bl"),
    LGT("lgt", "LG트윈스", "LG", "#BD0636", "ic_team_lgt", "ic_team_lgt_bl"),
    HHE("hhe", "한화이글스", "한화", "#F37321", "ic_team_hhe", "ic_team_hhe_bl"),
    SSL("ssl", "삼성라이온즈", "삼성", "#0064B2", "ic_team_ssl", "ic_team_ssl_bl"),
    KTW("ktw", "KT위즈", "KT", "#231F20", "ic_team_ktw", "ic_team_ktw_bl"),
    OTHER("", "", "", "", "", "");

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
