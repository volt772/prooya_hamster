package com.apx5.apx5.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtDelUser
import com.apx5.apx5.datum.pitcher.PtDelUser
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SettingViewModel
 */

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val prRepository: PrRepository
) : BaseViewModel<Any>()  {

    private val delUserResult = MutableLiveData<PrResource<CtDelUser>>()

    fun getDelUserResult(): LiveData<PrResource<CtDelUser>> = delUserResult

    /**
     * delRemoteUser
     * @desc 사용자 삭제 (Remote)
     */
    fun delRemoteUser(delUser: PtDelUser) {
        viewModelScope.launch {
            delUserResult.postValue(PrResource.loading(null))
            try {
                val result = prRepository.delUser(delUser)
                delUserResult.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                delUserResult.postValue(PrResource.error("[FAIL] Delete Remote User", null))
            }
        }
    }
}