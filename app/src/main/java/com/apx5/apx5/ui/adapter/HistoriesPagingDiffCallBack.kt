package com.apx5.apx5.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.apx5.apx5.paging.datum.HistoriesUi

/*
 * Created by Christopher Elias on 3/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class HistoriesPagingDiffCallBack : DiffUtil.ItemCallback<HistoriesUi>() {
    override fun areItemsTheSame(oldItem: HistoriesUi, newItem: HistoriesUi): Boolean {
        return oldItem.playId == newItem.playId
    }

    override fun areContentsTheSame(oldItem: HistoriesUi, newItem: HistoriesUi): Boolean {
        return oldItem == newItem
    }
}