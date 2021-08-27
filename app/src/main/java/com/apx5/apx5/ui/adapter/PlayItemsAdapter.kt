package com.apx5.apx5.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.ui.utilities.PrUtils
import javax.inject.Inject

/**
 * PlayItemsAdapter
 */
class PlayItemsAdapter(
    val context: Context,
    private val viewType: PrAdapterViewType,
    private val delGame: ((AdtPlayDelTarget) -> Unit)?= null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val games = mutableListOf<AdtGames>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_RECENT -> GameRecentViewHolder.create(parent)
            VIEW_TYPE_DETAIL -> GameDetailViewHolder.create(parent)
            VIEW_TYPE_ALL -> GameAllViewHolder.create(parent, delGame)
            else ->  throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_RECENT -> (holder as GameRecentViewHolder).bind(games[position])
            VIEW_TYPE_DETAIL -> (holder as GameDetailViewHolder).bind(games[position])
            VIEW_TYPE_ALL -> (holder as GameAllViewHolder).bind(games[position])
        }
    }

    override fun getItemViewType(position: Int) = when(viewType) {
        PrAdapterViewType.RECENT -> VIEW_TYPE_RECENT
        PrAdapterViewType.DETAIL -> VIEW_TYPE_DETAIL
        PrAdapterViewType.ALL -> VIEW_TYPE_ALL
    }

    override fun getItemCount() = games.size

    /* 아이템 추가*/
    internal fun addItem(game: AdtGames) {
        games.add(game)
    }

    /* 아이템 전체초기화*/
    internal fun clearItems() {
        games.clear()
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_RECENT = 1
        const val VIEW_TYPE_DETAIL = 2
        const val VIEW_TYPE_ALL = 3
    }
}