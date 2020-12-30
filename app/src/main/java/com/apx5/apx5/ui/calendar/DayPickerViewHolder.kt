package com.apx5.apx5.ui.calendar

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R
import kotlinx.android.synthetic.main.item_day_picker_day.view.*
import kotlinx.android.synthetic.main.item_day_picker_month.view.*

/**
 * DayPickerViewHolder
 */

internal abstract class DayPickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(item: DayPickerEntity, actionListener: (DayPickerEntity, Int) -> Unit)
}

/**
 * ViewHolder (월)
 */
internal class MonthViewHolder(private val view: View) : DayPickerViewHolder(view) {
    private val monthName by lazy { view.tv_month_name }
    override fun onBind(item: DayPickerEntity, actionListener: (DayPickerEntity, Int) -> Unit) {
        if (item is DayPickerEntity.Month) {
            monthName.text = item.label
        }
    }
}

/**
 * ViewHolder (주)
 */
internal open class WeekViewHolder(view: View) : DayPickerViewHolder(view) {
    override fun onBind(item: DayPickerEntity, actionListener: (DayPickerEntity, Int) -> Unit) {
    }
}

/**
 * ViewHolder (일)
 */
internal class DayViewHolder(view: View) : DayPickerViewHolder(view) {
    private val dayName by lazy { view.tv_day_name }

    override fun onBind(item: DayPickerEntity, actionListener: (DayPickerEntity, Int) -> Unit) {
        if (item is DayPickerEntity.Day) {
            dayName.text = item.label
            if (item.selection == SelectionType.SELECTED) {
                dayName.select()
            } else {
                dayName.deselect()
            }

            dayName.setTextColor(getFontColor(item))

            itemView.setOnClickListener {
                actionListener.invoke(
                    item,
                    bindingAdapterPosition
                )
            }
        }
    }

    /**
     * 글자 색상
     */
    private fun getFontColor(item: DayPickerEntity.Day): Int {
        val dayColor = if (item.selection != SelectionType.NONE) { R.color.p_white_10 } else { R.color.p_main_first }
        return ContextCompat.getColor(itemView.context, dayColor)
    }

    /**
     * 선택 Style (일반)
     */
    private fun View.select() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.bg_range_picker_selected_day)
        background = drawable
    }

    /**
     * 선택해제 Style
     */
    private fun View.deselect() {
        background = null
    }
}

internal class EmptyViewHolder(view: View) : WeekViewHolder(view)