package com.apx5.apx5.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.operation.PrResource
import com.apx5.domain.dto.ServerStatusDto
import com.apx5.domain.model.PrServerStatus
import com.apx5.domain.usecase.SplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SplashViewModel
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase
) : BaseViewModel() {

    private val serverStatus = MutableLiveData<PrResource<PrServerStatus>>()

    init {
        fetchServerStatus()
    }

    /**
     * fetchServerStatus
     * @desc 서버 검사
     */
    private fun fetchServerStatus() {
        viewModelScope.launch {
            serverStatus.postValue(PrResource.loading(null))
            try {
                val result = splashUseCase.serverStatus()
                val serverResult = PrResource.success(result)

                serverStatus.postValue(serverResult)
            } catch (e: Exception) {
                serverStatus.postValue(PrResource.error("[FAIL] Server Ping Check Error", null))
            }
        }
    }

    fun getServerStatus(): LiveData<PrResource<PrServerStatus>> = serverStatus
}