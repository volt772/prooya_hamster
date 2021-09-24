package com.apx5.apx5.ui.histories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.mapper.HistoriesMapper
import com.apx5.apx5.operation.PrResource
import com.apx5.domain.dto.HistoriesUi
import com.apx5.domain.model.PrDelHistory
import com.apx5.domain.param.HistoriesParam
import com.apx5.domain.param.HistoryDelParam
import com.apx5.domain.usecase.HistoriesUseCase
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
    private val historiesUseCase: HistoriesUseCase,
    private val mapper: HistoriesMapper
) : BaseViewModel() {

    private val delResult = MutableLiveData<PrResource<PrDelHistory>>()

    /**
     * getAllHistories
     * @desc Histories Paging Fetch
     */
    fun getAllHistories(param: HistoriesParam): Flow<PagingData<HistoriesUi>> {
        return historiesUseCase.fetchHistories(param)
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
    fun requestDelHistory(param: HistoryDelParam) {
        viewModelScope.launch {
            try {
                val result = historiesUseCase.delHistory(param)
                delResult.postValue(PrResource.success(result))
            } catch (e: Exception) {
                delResult.postValue(PrResource.error("[FAIL] Delete Failed For History", null))
            }
        }
    }

    fun getDelResult(): LiveData<PrResource<PrDelHistory>> = delResult
}