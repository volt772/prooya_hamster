package com.apx5.apx5.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.apx5.domain.dto.HistoriesUi

/**
 * HistoriesPagingDiffCallBack
 */
class HistoriesPagingDiffCallBack : DiffUtil.ItemCallback<HistoriesUi>() {
    override fun areItemsTheSame(oldItem: HistoriesUi, newItem: HistoriesUi): Boolean {
        return oldItem.playId == newItem.playId
    }

    override fun areContentsTheSame(oldItem: HistoriesUi, newItem: HistoriesUi): Boolean {
        return oldItem == newItem
    }
}