package com.apx5.apx5.ui.recordall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.datum.catcher.CtDelHistory
import com.apx5.apx5.datum.catcher.CtHistories
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import kotlinx.coroutines.launch

/**
 * RecordAllViewModel
 */

class RecordAllViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<Any>() {

    private val delHistory = MutableLiveData<PrResource<CtDelHistory>>()
    private val histories = MutableLiveData<PrResource<CtHistories>>()

    /* 기록 삭제*/
    fun requestDelHistory(play: PtDelHistory) {
        viewModelScope.launch {
            delHistory.postValue(PrResource.loading(null))
            try {
                val result = prRepository.delHistory(play)
                delHistory.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                delHistory.postValue(PrResource.error("Delete History Error", null))
            }
        }
    }

    /* 전체 데이터*/
    fun getAllPlayLists(email: String, year: Int) {
        viewModelScope.launch {
            histories.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getHistories(PtPostTeams(email, year))
                histories.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                histories.postValue(PrResource.error("Fetch History Error", null))
            }
        }
    }

    fun delHistory(): LiveData<PrResource<CtDelHistory>> = delHistory
    fun getHistories(): LiveData<PrResource<CtHistories>> = histories
}