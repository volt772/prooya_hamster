package com.apx5.apx5.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtPostUser
import com.apx5.apx5.datum.pitcher.PtPostUser
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import com.apx5.apx5.storage.PrPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TeamViewModel
 */

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val prRepository: PrRepository
) : BaseViewModel<Any>()  {

    @Inject
    lateinit var prPreference: PrPreference

    private val teamPostResult = MutableLiveData<PrResource<CtPostUser>>()

    fun getTeamPostResult(): LiveData<PrResource<CtPostUser>> = teamPostResult

    /**
     * saveTeam
     * @desc 사용자 정보 서버 저장
     */
    fun saveTeam(teamCode: String) {
        prPreference.userEmail?.let { _email ->
            if (_email.isNotBlank()) {
                viewModelScope.launch {
                    teamPostResult.postValue(PrResource.loading(null))
                    try {
                        val result = prRepository.postUser(PtPostUser(_email, teamCode))
                        teamPostResult.postValue(PrResource.success(result.data))
                    } catch (e: Exception) {
                        teamPostResult.postValue(PrResource.error("[FAIL] Post User", null))
                    }
                }
            }
        }
    }
}