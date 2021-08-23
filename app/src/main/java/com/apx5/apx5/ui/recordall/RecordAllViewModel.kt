package com.apx5.apx5.ui.recordall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtDelHistory
import com.apx5.apx5.datum.catcher.CtHistories
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * RecordAllViewModel
 */

@HiltViewModel
class RecordAllViewModel @Inject constructor(
    private val prRepository: PrRepository,
) : BaseViewModel<Any>() {

    private val histories = MutableLiveData<PrResource<CtHistories>>()
    private val delResult = MutableLiveData<PrResource<CtDelHistory>>()

    /* 전체 데이터*/
    fun getAllPlayLists(email: String, year: Int) {
        viewModelScope.launch {
            histories.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getHistories(PtPostTeams(email, year))
                histories.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                histories.postValue(PrResource.error("[FAIL] Load All Histories", null))
            }
        }
    }

    /* 기록 삭제*/
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


    fun getHistories(): LiveData<PrResource<CtHistories>> = histories
    fun getDelResult(): LiveData<PrResource<CtDelHistory>> = delResult
}