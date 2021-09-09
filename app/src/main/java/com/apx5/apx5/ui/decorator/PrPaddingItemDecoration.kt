package com.apx5.apx5.ui.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.ui.utilities.PrUtils

/**
 * MpPaddingItemDecoration
 * @desc Padding Decoration
 * @param allHorizontalPadding 모든 Item 좌,우 간격
 * @param additionalVerticalPadding 첫 Item의 Top, 마지막 Item의 Bottom의 추가 간격
 * @param allHorizontalPadding 모든 Item 상,하 간격
 */
class PrPaddingItemDecoration(
    val prUtils: PrUtils,
    /* Padding : 좌,우 간격 (Except First and Last Items)*/
    private val allHorizontalPadding: Int = NOT_SET,
    /* Padding : 상,하 추가간격 (Only First Item and Last Items)*/
    private val additionalVerticalPadding: Int = NOT_SET,
    /* Padding : 상,하 기본간격 (Except First Item and Last Items)*/
    private val defaultVerticalPadding: Int = NOT_SET,
) : RecyclerView.ItemDecoration() {


    companion object {
        private const val NOT_SET = 0
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val (_left, _top, _right, _bottom) =
            if (state.itemCount == 1) {
                /* One Item*/
                listOf(
                    allHorizontalPadding,
                    defaultVerticalPadding,
                    allHorizontalPadding,
                    defaultVerticalPadding
                )
            } else {
                /* Multi Item*/
                if (isFirstItem(parent, view) && additionalVerticalPadding != NOT_SET) {
                    /* First Item*/
                    listOf(
                        allHorizontalPadding,
                        (additionalVerticalPadding + defaultVerticalPadding),
                        allHorizontalPadding,
                        defaultVerticalPadding
                    )
                } else if (isLastItem(parent, view, state) && additionalVerticalPadding != NOT_SET) {
                    /* Last Item*/
                    listOf(
                        allHorizontalPadding,
                        defaultVerticalPadding,
                        allHorizontalPadding,
                        (additionalVerticalPadding + defaultVerticalPadding)
                    )
                } else {
                    /* Else*/
                    listOf(
                        allHorizontalPadding,
                        defaultVerticalPadding,
                        allHorizontalPadding,
                        defaultVerticalPadding
                    )
                }
            }

        view.setPadding(prUtils.dpToPx(_left), prUtils.dpToPx(_top), prUtils.dpToPx(_right), prUtils.dpToPx(_bottom))
    }

    private fun isFirstItem(parent: RecyclerView, view: View) =
        parent.getChildAdapterPosition(view) == 0

    private fun isLastItem(parent: RecyclerView, view: View, state: RecyclerView.State) =
        parent.getChildAdapterPosition(view) == state.itemCount - 1
}