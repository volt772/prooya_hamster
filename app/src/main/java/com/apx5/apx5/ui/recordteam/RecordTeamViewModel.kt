package com.apx5.apx5.ui.recordteam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtGetRecordDetail
import com.apx5.apx5.datum.catcher.CtPostTeams
import com.apx5.apx5.datum.pitcher.PtGetRecordDetail
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * RecordTeamViewModel
 */

@HiltViewModel
class RecordTeamViewModel @Inject constructor(
    private val prRepository: PrRepository,
) : BaseViewModel<Any>() {

    private val details = MutableLiveData<PrResource<CtGetRecordDetail>>()
    private val teams = MutableLiveData<PrResource<CtPostTeams>>()

    /* 팀간 상세 기록*/
    fun fetchDetails(email: String, versus: String, year: Int) {
        viewModelScope.launch {
            details.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getRecordDetail(PtGetRecordDetail(email, versus, year))
                details.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                details.postValue(PrResource.error("[FAIL] Load Detail", null))
            }
        }
    }

    /* 팀간 기록*/
    fun fetchRecords(email: String, year: Int) {
        viewModelScope.launch {
            teams.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getRecordByTeams(PtPostTeams(email, year))
                teams.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                teams.postValue(PrResource.error("[FAIL] Load Records", null))
            }
        }
    }

    fun getDetails(): LiveData<PrResource<CtGetRecordDetail>> = details
    fun getTeams(): LiveData<PrResource<CtPostTeams>> = teams
}