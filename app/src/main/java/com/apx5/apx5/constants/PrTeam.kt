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
        val subColor: String) {
    KAT("kat", "기아타이거즈", "기아", "#AF1D2B", "#0B203F"),
    DSB("dsb", "두산베어스", "두산", "#131230", "#838585"),
    LTG("ltg", "롯데자이언츠", "롯데", "#DD0330", "#DD0330"),
    NCD("ncd", "NC다이노스", "NC", "#1D467C", "#B0927C"),
    SKW("skw", "SK와이번스", "SK", "#E85021", "#EF800A"),
    NXH("nxh", "키움히어로즈", "키움", "#830125", "#A7A9AC"),
    LGT("lgt", "LG트윈스", "LG", "#BD0636", "#1C1C1A"),
    HHE("hhe", "한화이글스", "한화", "#F37321", "#3D4142"),
    SSL("ssl", "삼성라이온즈", "삼성", "#0064B2", "#BDBEC3"),
    KTW("ktw", "KT위즈", "KT", "#231F20", "#ED1C24"),
    OTHER("", "", "", "", "");

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
