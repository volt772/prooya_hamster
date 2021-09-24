package com.apx5.apx5.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.operation.PrResource
import com.apx5.domain.model.PrDelUser
import com.apx5.domain.param.UserDelParam
import com.apx5.domain.usecase.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SettingViewModel
 */

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase
) : BaseViewModel()  {

    private val delUserResult = MutableLiveData<PrResource<PrDelUser>>()

    /**
     * delRemoteUser
     * @desc 사용자 삭제 (Remote)
     */
    fun delRemoteUser(param: UserDelParam) {
        viewModelScope.launch {
            delUserResult.postValue(PrResource.loading(null))
            try {
                val result = settingUseCase.delUser(param)
                delUserResult.postValue(PrResource.success(result))
            } catch (e: Exception) {
                delUserResult.postValue(PrResource.error("[FAIL] Delete Remote User", null))
            }
        }
    }

    fun getDelUserResult(): LiveData<PrResource<PrDelUser>> = delUserResult
}