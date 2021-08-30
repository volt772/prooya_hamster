package com.apx5.apx5.ui.utilities

import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import com.apx5.apx5.ProoyaClient.Companion.appContext
import com.apx5.apx5.constants.PrWinningStatus
import com.apx5.apx5.ext.equalsExt
import com.apx5.apx5.ext.splitExt
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.util.*
import javax.inject.Inject

/**
 * PrUtilsImpl
 */

class PrUtilsImpl @Inject constructor() : PrUtils {

    /**
     * 현재연도 (y)
     * @return ex) 2020
     */
    override val currentYear: Int
        get() {
            val date = DateTime()

            return if (date.year > 2020) { 2020 } else { date.year }
        }

    /**
     * 현재일자
     * @return ex) 20201010
     */
    override val today: String
        get() {
            val localDate = LocalDate()
            return localDate.toString().replace("-", "")
        }

    /**
     * 날자 변환
     * @return ex) 21. 10. 11.
     */
    override fun getDateToFull(dateString: String): String {
        if (TextUtils.isEmpty(dateString)) {
            return ""
        }

        val year = dateString.substring(2, 4)
        val month = dateString.substring(4, 6)
        val day = dateString.substring(6, 8)

        return String.format(Locale.getDefault(), "%s. %s. %s", year, month, day)
    }

    /**
     * 시간 변환
     * @return ex) 16:30
     */
    override fun getTime(time: String): String {
        if (time.isBlank()) return ""

        /* 경기시작시간 없는경우*/
        return if (time.equalsExt("0")) {
            ""
        } else {
            val hour = time.substring(0, 2)
            val min = time.substring(2, 4)
            String.format(Locale.getDefault(), "%s:%s", hour, min)
        }
    }

    /**
     * 날자 변환 (Readable)
     * @return ex) 03월 18일
     */
    override fun getDateToReadableMonthDay(dateString: String): String {
        if (TextUtils.isEmpty(dateString)) {
            return ""
        }

        val month = dateString.substring(4, 6)
        val day = dateString.substring(6, 8)

        return String.format(Locale.getDefault(), "%s월 %s일", month, day)
    }

    /**
     * 연도
     * @return ex) 2020
     */
    override fun getYear(dateString: String): String {
        return if (dateString.isBlank()) "" else dateString.substring(0, 4)
    }

    /**
     * 날자변환
     * @return ex) 2020.03.19
     */
    override fun getDateToAbbr(dateString: String, divider: String): String {
        if (dateString.isBlank()) return ""

        val year = dateString.substring(2, 4)
        val month = dateString.substring(4, 6)
        val day = dateString.substring(6, 8)

        return String.format(Locale.getDefault(), "%s%s%s%s%s", year, divider, month, divider, day)
    }

    /**
     * 날자분리
     * @return ex) year -> 2021, month -> 03, day -> 11
     */
    override fun getTodaySeparate(type: String): Int {
        val localDate = LocalDate()
        val today = localDate.toString()
        val todayArr = today.splitExt("-")

        when (type) {
            "year" -> return Integer.parseInt(todayArr[0])
            "month" -> return Integer.parseInt(todayArr[1])
            "day" -> return Integer.parseInt(todayArr[2])
        }

        return 0
    }

    /**
     * Drawable Int
     */
    override fun getDrawableByName(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }

    /**
     * 승팀 사이드 구분
     * @desc 원정팀승, 홈팀승, 무승부
     */
    override fun distinguishWinning(away: Int, home: Int) = when {
        away > home -> PrWinningStatus.AWAY
        away < home -> PrWinningStatus.HOME
        else -> PrWinningStatus.BOTH
    }

    /**
     * Dp To Px
     * @desc DP convert to Pixel
     */
    override fun dpToPx(dp: Int) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), appContext.resources.displayMetrics
    ).toInt()

    /**
     * getFileContents
     * @desc Read File Content using Builder
     */
    override fun getFileContents(dir: String, fileName: String): String {
        val contents = StringBuilder().apply {
            val filePath = String.format("%s/%s", dir, fileName)
            appContext.assets.open(filePath).bufferedReader().use {
                append(it.readText())
            }
        }

        return contents.toString()
    }
}