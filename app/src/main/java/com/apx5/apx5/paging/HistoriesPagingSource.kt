package com.apx5.apx5.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.paging.datum.HistoriesResponse
import com.apx5.apx5.repository.PrRepository
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/*
 * Created by Christopher Elias on 7/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

private const val TMDB_STARTING_PAGE_INDEX = 1


class HistoriesPagingSource(
    private val prRepository: PrRepository,
) : PagingSource<Int, HistoriesResponse>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoriesResponse> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        println("probe : HistoriesPagingSource")
        return try {
            val response = prRepository.getPagingHistories(PtPostTeams(email = "volt772@naver.com", year = 0))
            val histories = response.games
            println("probe : histories : ${histories}")
//            val response = service.getTopRatedMovies(
//                language = "en-US",
//                page = pageIndex
//            )
//            val movies = response.results
            val nextKey =
                if (histories.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = histories,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            println("probe : IOException : ${exception}")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            println("probe : HttpException : ${exception}")
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            println("probe : Exception : ${exception}")
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