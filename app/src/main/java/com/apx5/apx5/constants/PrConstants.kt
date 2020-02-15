package com.apx5.apx5.constants

object PrConstants {
    object App {
        const val FLK_HOST = "http://prooya772.vps.phps.kr:6100"
    }

    object Fcm {
        const val TITLE = "title"
        const val DATE = "pdate"
        const val AWAYCODE = "acode"
        const val HOMECODE = "hcode"
        const val AWAYSCORE = "ascore"
        const val HOMESCORE = "hscore"
    }

    object Codes {
        const val STANDBY = 998
        const val ONPLAY = 997
        const val CANCELED = 999
        const val FINE = 1000
        const val WIN = "승"
        const val DRAW = "무"
        const val LOSE = "패"
    }

    object Teams {
        const val EMBLEM_PREFIX = "ic_team_"
        val FULL: Map<String, String> = mapOf(
                Pair("kat", "기아타이거즈"),
                Pair("dsb", "두산베어스"),
                Pair("ltg", "롯데자이언츠"),
                Pair("ncd", "NC다이노스"),
                Pair("skw", "SK와이번스"),
                Pair("nxh", "키움히어로즈"),
                Pair("nxhOld", "넥센히어로즈"),
                Pair("lgt", "LG트윈스"),
                Pair("hhe", "한화이글스"),
                Pair("ssl", "삼성라이온즈"),
                Pair("ktw", "KT 위즈")
        )

        val ABBR: Map<String, String> = mapOf(
            Pair("kat", "기아"),
            Pair("dsb", "두산"),
            Pair("ltg", "롯데"),
            Pair("ncd", "NC"),
            Pair("skw", "SK"),
            Pair("nxh", "키움"),
            Pair("nxhOld", "넥센"),
            Pair("lgt", "LG"),
            Pair("hhe", "한화"),
            Pair("ssl", "삼성"),
            Pair("ktw", "KT")
        )

        val BICOLOR: Map<String, String> = mapOf(
            Pair("kat", "#AF1D2B"),
            Pair("dsb", "#131230"),
            Pair("ltg", "#DD0330"),
            Pair("ncd", "#1D467C"),
            Pair("skw", "#E85021"),
            Pair("nxh", "#830125"),
            Pair("lgt", "#BD0636"),
            Pair("hhe", "#F37321"),
            Pair("ssl", "#0064B2"),
            Pair("ktw", "#231F20"),
            Pair("katSub", "#0B203F"),
            Pair("dsbSub", "#838585"),
            Pair("ltgSub", "#DD0330"),
            Pair("ncdSub", "#B0927C"),
            Pair("skwSub", "#EF800A"),
            Pair("nxhSub", "#A7A9AC"),
            Pair("lgtSub", "#1C1C1A"),
            Pair("hheSub", "#3D4142"),
            Pair("sslSub", "#BDBEC3"),
            Pair("ktwSub", "#ED1C24")
        )
    }
}
