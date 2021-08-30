package com.apx5.apx5.ext

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView decoration (single)
 */
fun RecyclerView?.itemDecorationExt(deco: RecyclerView.ItemDecoration) {
    this?.addItemDecoration(deco)
}

/**
 * RecyclerView decoration (multi)
 */
fun RecyclerView?.itemDecorationExt(deco: List<RecyclerView.ItemDecoration>) {
    deco.forEach { _deco ->
        this?.addItemDecoration(_deco)
    }
}

/**
 * DividerItemDecorator
 * @desc 리사이클러뷰 마지막 아이템 밑줄 제외하고 밑줄 그려주기
 */
class DividerItemDecorator(
    private val divider: Drawable?,
    private val padding: Int = 0,
) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        divider?.let { _drawable ->
            val dividerLeft = if (padding != 0) padding else parent.paddingLeft
            val dividerRight = parent.width - if (padding != 0) padding else parent.paddingRight
            val childCount = parent.childCount
            for (_count in 0 until childCount - 1) {
                val child: View = parent.getChildAt(_count)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + _drawable.intrinsicHeight

                _drawable.apply {
                    setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                    draw(c)
                }
            }
        }
    }
}