package com.apx5.apx5.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.ProoyaClient.Companion.appContext
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.datum.catcher.CtPostUser
import com.apx5.apx5.datum.pitcher.PtPostUser
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import com.apx5.apx5.storage.PrefManager
import kotlinx.coroutines.launch

/**
 * TeamViewModel
 */

class TeamViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<TeamNavigator>()  {

    private val teamPostResult = MutableLiveData<PrResource<CtPostUser>>()

    fun getTeamPostResult(): LiveData<PrResource<CtPostUser>> = teamPostResult

    /**
     * 사용자 정보 서버 저장
     */
    fun saveTeam(teamCode: String) {
        val email = PrefManager.getInstance(appContext).userEmail
        email?.let {
            if (it.isNotBlank()) {
                viewModelScope.launch {
                    teamPostResult.postValue(PrResource.loading(null))
                    try {
                        val result = prRepository.postUser(PtPostUser(email, teamCode))
                        teamPostResult.postValue(PrResource.success(result.data))
                    } catch (e: Exception) {
                        teamPostResult.postValue(PrResource.error("Post User Error", null))
                    }
                }
            }
        }
    }
}

