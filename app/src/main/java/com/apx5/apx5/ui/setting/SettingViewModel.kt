package com.apx5.apx5.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.datum.catcher.CtDelUser
import com.apx5.apx5.datum.pitcher.PtDelUser
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import kotlinx.coroutines.launch

/**
 * SettingViewModel
 */

class SettingViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<Any>()  {

    private val delUserResult = MutableLiveData<PrResource<CtDelUser>>()

    fun getDelUserResult(): LiveData<PrResource<CtDelUser>> = delUserResult

    /* 사용자 삭제 (Remote)*/
    fun delRemoteUser(delUser: PtDelUser) {
        viewModelScope.launch {
            delUserResult.postValue(PrResource.loading(null))
            try {
                val result = prRepository.delUser(delUser)
                delUserResult.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                delUserResult.postValue(PrResource.error("Fail Delete Remote User", null))
            }
        }
    }
}
