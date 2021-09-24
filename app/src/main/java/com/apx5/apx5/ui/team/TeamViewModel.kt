package com.apx5.apx5.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.operation.PrResource
import com.apx5.apx5.storage.PrPreference
import com.apx5.domain.model.PrUserRegister
import com.apx5.domain.param.UserRegisterParam
import com.apx5.domain.usecase.TeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TeamViewModel
 */

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val teamUseCase: TeamUseCase
) : BaseViewModel()  {

    @Inject
    lateinit var prPreference: PrPreference

    private val teamRegisterResult = MutableLiveData<PrResource<PrUserRegister>>()

    /**
     * saveTeam
     * @desc 사용자 정보 서버 저장
     */
    fun saveTeam(teamCode: String) {
        prPreference.userEmail?.let { _email ->
            if (_email.isNotBlank()) {
                viewModelScope.launch {
                    teamRegisterResult.postValue(PrResource.loading(null))
                    try {
                        val result = teamUseCase.postUser(UserRegisterParam(_email, teamCode))
                        teamRegisterResult.postValue(PrResource.success(result))
                    } catch (e: Exception) {
                        teamRegisterResult.postValue(PrResource.error("[FAIL] Post User", null))
                    }
                }
            }
        }
    }

    fun getTeamPostResult(): LiveData<PrResource<PrUserRegister>> = teamRegisterResult
}