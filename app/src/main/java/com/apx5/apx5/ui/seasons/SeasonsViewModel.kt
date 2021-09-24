package com.apx5.apx5.ui.seasons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.operation.PrResource
import com.apx5.domain.model.PrTeamDetail
import com.apx5.domain.model.PrTeamSummary
import com.apx5.domain.param.TeamDetailParam
import com.apx5.domain.param.TeamSummaryParam
import com.apx5.domain.usecase.SeasonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SeasonsViewModel
 */

@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val seasonsUseCase: SeasonsUseCase
) : BaseViewModel() {

    private val details = MutableLiveData<PrResource<PrTeamDetail>>()
    private val teams = MutableLiveData<PrResource<PrTeamSummary>>()

    /**
     * fetchDetails
     * @desc 팀간 상세 기록
     */
    fun fetchDetails(email: String, versus: String, year: Int) {
        viewModelScope.launch {
            details.postValue(PrResource.loading(null))
            try {
                val result = seasonsUseCase.fetchRecordDetail(TeamDetailParam(email, versus, year))
                details.postValue(PrResource.success(result))
            } catch (e: Exception) {
                details.postValue(PrResource.error("[FAIL] Load Detail", null))
            }
        }
    }

    /**
     * fetchRecords
     * @desc 팀간 기록
     */
    fun fetchRecords(email: String, year: Int) {
        viewModelScope.launch {
            teams.postValue(PrResource.loading(null))
            try {
                val result = seasonsUseCase.fetchRecordByTeams(TeamSummaryParam(email, year))
                teams.postValue(PrResource.success(result))
            } catch (e: Exception) {
                teams.postValue(PrResource.error("[FAIL] Load Records", null))
            }
        }
    }

    fun getDetails(): LiveData<PrResource<PrTeamDetail>> = details
    fun getTeams(): LiveData<PrResource<PrTeamSummary>> = teams
}