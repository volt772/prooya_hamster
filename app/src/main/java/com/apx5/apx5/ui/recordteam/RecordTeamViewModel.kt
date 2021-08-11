package com.apx5.apx5.ui.recordteam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.datum.catcher.CtGetRecordDetail
import com.apx5.apx5.datum.catcher.CtPostTeams
import com.apx5.apx5.datum.pitcher.PtGetRecordDetail
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import kotlinx.coroutines.launch

/**
 * RecordTeamViewModel
 */

class RecordTeamViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<Any>() {

    private val details = MutableLiveData<PrResource<CtGetRecordDetail>>()
    private val teams = MutableLiveData<PrResource<CtPostTeams>>()

    /* 팀 상세 데이터*/
    fun fetchDetails(email: String, versus: String, year: Int) {
        viewModelScope.launch {
            details.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getRecordDetail(PtGetRecordDetail(email, versus, year))
                details.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                details.postValue(PrResource.error("Fetch Details For $versus Error", null))
            }
        }
    }

    /* 팀 상세 데이터 다운로드*/
    fun fetchRecords(email: String, year: Int) {
        viewModelScope.launch {
            teams.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getRecordByTeams(PtPostTeams(email, year))
                teams.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                teams.postValue(PrResource.error("Fetch Team List Data Error", null))
            }
        }
    }

    fun getDetails(): LiveData<PrResource<CtGetRecordDetail>> = details
    fun getTeams(): LiveData<PrResource<CtPostTeams>> = teams
}