package com.apx5.apx5.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.catcher.CtPing
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SplashViewModel
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: PrRepository
) : BaseViewModel<Any>() {

    private val serverStatus = MutableLiveData<PrResource<CtPing>>()

    init {
        fetchServerStatus()
    }

    /* 서버 검사*/
    private fun fetchServerStatus() {
        viewModelScope.launch {
            serverStatus.postValue(PrResource.loading(null))
            try {
                val result = repository.getServerStatus()
                val serverResult = PrResource.success(result.data)

                serverStatus.postValue(serverResult)
            } catch (e: Exception) {
                serverStatus.postValue(PrResource.error("Server Check Error", null))
            }
        }
    }

    fun getServerStatus(): LiveData<PrResource<CtPing>> = serverStatus
}