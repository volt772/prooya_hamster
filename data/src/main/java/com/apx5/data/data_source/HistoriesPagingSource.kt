package com.apx5.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.param.HistoriesParam
import com.apx5.domain.repository.PrRepository
import retrofit2.HttpException
import java.io.IOException

/**
 * HistoriesPagingSource
 */
private const val TMDB_STARTING_PAGE_INDEX = 1

class HistoriesPagingSource(
    private val prRepository: PrRepository,
    val ptPostTeams: HistoriesParam
) : PagingSource<Int, HistoriesResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoriesResponse> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = prRepository.getPagingHistories(ptPostTeams, pageIndex, NETWORK_PAGE_SIZE)
            val histories = response.games

            val nextKey = if (histories.isNullOrEmpty() || histories.size < NETWORK_PAGE_SIZE) {
                null
            } else {
                pageIndex + 1
            }

            LoadResult.Page(
                data = histories,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, HistoriesResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}


/**
 * SAMPLE
 *
 * val nextKey =
 * if (histories.isEmpty()) {
 * null
 * } else {
 * // By default, initial load size = 3 * NETWORK PAGE SIZE
 * // ensure we're not requesting duplicating items at the 2nd request
 * pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
 * }
 *
 * LoadResult.Page(
 * data = histories,
 * prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
 * nextKey = pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
 * )
 */