package com.apx5.apx5.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.databinding.ItemHistoriesAllBinding
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.paging.datum.HistoriesUi
import com.apx5.apx5.ui.utilities.PrUtils
import javax.inject.Inject

/**
 * HistoriesPagingAdapter
 */
class HistoriesPagingAdapter @Inject constructor(
    val prUtils: PrUtils,
    private val delGame: ((AdtPlayDelTarget) -> Unit)?= null,
) : PagingDataAdapter<HistoriesUi, HistoriesViewHolder>(HistoriesPagingDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoriesViewHolder {
        val holder = HistoriesViewHolder(
            ItemHistoriesAllBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        holder.binding.root.setOnLongClickListener {
            getItem(holder.adapterPosition)?.let { game ->
                delGame?.let { _callback ->
                    _callback(
                        AdtPlayDelTarget(
                            id = game.playId?: 0,
                            season = game.playSeason?: 0,
                            versus = game.playVs?: "",
                            result = PrResultCode.getResultByDisplayCode(game.playResult).codeAbbr
                        )
                    )
                }
            }
            true
        }

        return holder
    }

    override fun onBindViewHolder(holder: HistoriesViewHolder, position: Int) {
        holder.bind(getItem(position), prUtils)
    }
}