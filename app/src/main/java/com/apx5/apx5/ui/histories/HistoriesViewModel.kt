package com.apx5.apx5.ui.histories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtDelHistory
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.paging.HistoriesMapper
import com.apx5.apx5.paging.HistoriesRepository
import com.apx5.apx5.paging.datum.HistoriesUi
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HistoriesViewModel
 */

@HiltViewModel
class HistoriesViewModel @Inject constructor(
    private val prRepository: PrRepository,
    private val historiesRepository: HistoriesRepository,
    private val mapper: HistoriesMapper
) : BaseViewModel<Any>() {

    private val delResult = MutableLiveData<PrResource<CtDelHistory>>()

    /**
     * getAllHistories
     * @desc Histories Paging Fetch
     */
    fun getAllHistories(ptPostTeams: PtPostTeams): Flow<PagingData<HistoriesUi>> {
        return historiesRepository.getHistories(ptPostTeams)
            .map { pagingData ->
                pagingData.map {
                    mapper.mapDomainHistoriesToUi(domainHistories = it)
                }
            }
            .cachedIn(viewModelScope)
    }

    /**
     * requestDelHistory
     * @desc 기록삭제
     */
    fun requestDelHistory(play: PtDelHistory) {
        viewModelScope.launch {
            try {
                val result = prRepository.delHistory(play)
                delResult.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                delResult.postValue(PrResource.error("[FAIL] Delete Failed For History", null))
            }
        }
    }


    fun getDelResult(): LiveData<PrResource<CtDelHistory>> = delResult
}