package com.apx5.apx5.ui.calendar

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.R

/**
 * RangePickerAdapter
 */

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachedToRoot: Boolean = false): View =
    from(context).inflate(layoutId, this, attachedToRoot)

internal class DayPickerAdapter : RecyclerView.Adapter<DayPickerViewHolder>() {
    private val data: MutableList<DayPickerEntity> = mutableListOf()
    var onActionListener: (DayPickerEntity, Int) -> Unit = { _, _ -> }

    /**
     * Data 설정
     * @desc 일자 중복으로 DiffCallback 사용
     */
    fun setData(newData: List<DayPickerEntity>) {
        val diffCallback = DayPickerDiffCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * ViewHolder 생성
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPickerViewHolder {
        return when (viewType) {
            CalendarType.MONTH.ordinal -> MonthViewHolder(parent.inflate(R.layout.item_day_picker_month))
            CalendarType.DAY.ordinal -> DayViewHolder(parent.inflate(R.layout.item_day_picker_day))
            else -> EmptyViewHolder(parent.inflate(R.layout.item_day_picker_empty))
        }
    }

    /**
     * Item Count
     */
    override fun getItemCount(): Int = data.size

    /**
     * Binding
     */
    override fun onBindViewHolder(holder: DayPickerViewHolder, position: Int) {
        holder.onBind(data[position], onActionListener)
    }

    /**
     * ViewType
     */
    override fun getItemViewType(position: Int) = data[position].calendarType
}

/**
 * DiffCallback
 */
internal class DayPickerDiffCallback(
    private val oldList: List<DayPickerEntity>,
    private val newList: List<DayPickerEntity>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]::class == newList[newItemPosition]::class
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldList[oldItemPosition] is DayPickerEntity.Day && newList[newItemPosition] is DayPickerEntity.Day) {
            val oldDay = oldList[oldItemPosition] as DayPickerEntity.Day
            val newDay = newList[newItemPosition] as DayPickerEntity.Day
            oldDay.selection == newDay.selection
        } else {
            oldList[oldItemPosition].selectionType == newList[newItemPosition].selectionType
        }
    }
}
