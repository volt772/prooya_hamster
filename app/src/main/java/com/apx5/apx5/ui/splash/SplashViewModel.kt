package com.apx5.apx5.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import com.apx5.domain.dto.ServerStatusVO
import com.apx5.domain.usecase.CheckServerStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SplashViewModel
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: PrRepository,
    private val hamsterServerStatus: CheckServerStatus
) : BaseViewModel<Any>() {

    private val serverStatus = MutableLiveData<PrResource<ServerStatusVO>>()

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
                val result = hamsterServerStatus.execute()
                val serverResult = PrResource.success(result)

                serverStatus.postValue(serverResult)
            } catch (e: Exception) {
                serverStatus.postValue(PrResource.error("[FAIL] Server Ping Check Error", null))
            }
        }
    }

    fun getServerStatus(): LiveData<PrResource<ServerStatusVO>> = serverStatus
}